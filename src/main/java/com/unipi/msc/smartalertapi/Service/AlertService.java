package com.unipi.msc.smartalertapi.Service;

import com.unipi.msc.smartalertapi.Interface.IAlert;
import com.unipi.msc.smartalertapi.Interface.IMessage;
import com.unipi.msc.smartalertapi.Model.Alert.Alert;
import com.unipi.msc.smartalertapi.Model.Alert.AlertRepository;
import com.unipi.msc.smartalertapi.Model.Image.Image;
import com.unipi.msc.smartalertapi.Model.Image.ImageRepository;
import com.unipi.msc.smartalertapi.Model.Disaster.Disaster;
import com.unipi.msc.smartalertapi.Model.Disaster.DisasterRepository;
import com.unipi.msc.smartalertapi.Model.User.Officer;
import com.unipi.msc.smartalertapi.Model.User.User;
import com.unipi.msc.smartalertapi.Request.AlertRequest;
import com.unipi.msc.smartalertapi.Response.AlertPresenter;
import com.unipi.msc.smartalertapi.Response.GenericResponse;
import com.unipi.msc.smartalertapi.Shared.ErrorMessages;
import com.unipi.msc.smartalertapi.Shared.ImageUtils;
import com.unipi.msc.smartalertapi.Shared.Tags;
import com.unipi.msc.smartalertapi.Shared.Tools;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AlertService implements IAlert {
    private final AlertRepository alertRepository;
    private final DisasterRepository disasterRepository;
    private final ImageRepository imageRepository;
    private final IMessage iMessage;

    @Override
    public ResponseEntity<?> createAlert(AlertRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (request.getLatitude() == 0.      || request.getLongitude() == 0. ||
            request.getTimestamp() == null   || request.getComments().isEmpty() ||
            request.getDangerLevel() == null || request.getDisasterId() == null){
            return GenericResponse.builder().message(ErrorMessages.FILL_OBLIGATORY_FIELDS).build().badRequest();
        }
        Disaster disaster = disasterRepository.findById(request.getDisasterId()).orElse(null);
        if (disaster == null) return GenericResponse.builder().message(ErrorMessages.RISK_NOT_FOUND).build().badRequest();

        Image image = null;
        if (request.getImage() != null){
            try {
                image = Image.builder()
                        .type(request.getImage().getContentType())
                        .name(request.getImage().getName())
                        .imageData(ImageUtils.compressImage(request.getImage().getBytes()))
                        .build();
                image = imageRepository.save(image);
            } catch (Exception ignore) {}
        }

        Alert alert = Alert.builder()
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .timestamp(request.getTimestamp())
                .comments(request.getComments())
                .disaster(disaster)
                .image(image)
                .notified(false)
                .dangerLevel(request.getDangerLevel())
                .user(user)
                .build();
        alert = alertRepository.save(alert);

        if (checkAutoNotify(alert)) {
            alert.setNotified(true);
            alert = alertRepository.save(alert);
            iMessage.sendNotification(alert);
        }

        return ResponseEntity.ok(GenericResponse.builder().data(alert).build().success());
    }

    private boolean checkAutoNotify(Alert alert) {
        Long timestamp = Tools.getHoursBefore(Tags.HOURS_BEFORE);
        if (alertRepository.existsByTimestampGreaterThanAndNotifiedTrueAndDisasterEquals(timestamp,alert.getDisaster())){
            return false;
        }

        List<Alert> lastAlerts = alertRepository.findAllByTimestampGreaterThanOrderByTimestampDesc(timestamp);
        double closeAlerts = 0;
        double sameAlert = 0;
        for (Alert a:lastAlerts) {
            double distance = Tools.distance(a.getLatitude(),alert.getLatitude(),a.getLongitude(),alert.getLongitude());
            if (distance <= Tags.DISTANCE_LIMIT){
                closeAlerts += 1;
                if (a.getDisaster() == alert.getDisaster()) {
                    switch (alert.getDangerLevel()){
                        case LOW -> sameAlert += 0.5;
                        case MEDIUM -> sameAlert += 1;
                        case HIGH -> sameAlert += 2;
                    }
                }
            }
        }
        if (closeAlerts <= Tags.MIN_SIMILAR_EVENTS) return false;

        double percentageOfSameAlerts = sameAlert / closeAlerts * 100.0;
        return percentageOfSameAlerts >= Tags.MIN_PERCENTAGE_SIMILAR_ALERTS;
    }

    @Override
    public ResponseEntity<?> notify(Long id) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(user instanceof Officer)){
            return GenericResponse.builder().message(ErrorMessages.ACCESS_DENIED).build().badRequest();
        }

        Alert alert = alertRepository.findById(id).orElse(null);
        if (alert == null) return GenericResponse.builder().message(ErrorMessages.ALERT_NOT_FOUND).build().badRequest();

        alert.setNotified(true);
        alert = alertRepository.save(alert);
        iMessage.sendNotification(alert);
        return GenericResponse.builder().data(alert).build().success();
    }

    @Override
    public ResponseEntity<?> getAlerts() {
        List<AlertPresenter> presenter = new ArrayList<>();
        for (Alert alert: alertRepository.findAll()) {
            presenter.add(AlertPresenter.getPresenter(alert));
        }
        return GenericResponse.builder().data(presenter).build().success();
    }

    @Override
    public ResponseEntity<?> getAlert(Long id) {
        Alert alert = alertRepository.findById(id).orElse(null);
        if (alert == null) return GenericResponse.builder().message(ErrorMessages.ALERT_NOT_FOUND).build().badRequest();
        return GenericResponse.builder().data(AlertPresenter.getPresenter(alert)).build().success();
    }

    @Override
    public ResponseEntity<?> getNotifiedAlerts() {
        List<AlertPresenter> alertPresenters = new ArrayList<>();
        Long timestamp = Tools.getDaysBefore(2);

        for (Alert alert : alertRepository.findAllByNotifiedTrueAndTimestampGreaterThanOrderByTimestampDesc(timestamp)){
            alertPresenters.add(AlertPresenter.getPresenter(alert));
        }

        return GenericResponse.builder().data(alertPresenters).build().success();
    }
}