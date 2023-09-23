package com.unipi.msc.smartalertapi.Service;

import com.unipi.msc.smartalertapi.Interface.IAlert;
import com.unipi.msc.smartalertapi.Model.Alert.Alert;
import com.unipi.msc.smartalertapi.Model.Alert.AlertRepository;
import com.unipi.msc.smartalertapi.Model.Image.Image;
import com.unipi.msc.smartalertapi.Model.Image.ImageRepository;
import com.unipi.msc.smartalertapi.Model.Risk.Risk;
import com.unipi.msc.smartalertapi.Model.Risk.RiskRepository;
import com.unipi.msc.smartalertapi.Model.User.Officer;
import com.unipi.msc.smartalertapi.Model.User.User;
import com.unipi.msc.smartalertapi.Request.AlertRequest;
import com.unipi.msc.smartalertapi.Response.AlertPresenter;
import com.unipi.msc.smartalertapi.Response.ErrorResponse;
import com.unipi.msc.smartalertapi.Shared.ErrorMessages;
import com.unipi.msc.smartalertapi.Shared.ImageUtils;
import com.unipi.msc.smartalertapi.Shared.Tools;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AlertService implements IAlert {
    private final AlertRepository alertRepository;
    private final RiskRepository riskRepository;
    private final ImageRepository imageRepository;
    @Override
    public ResponseEntity<?> createAlert(AlertRequest request) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(user instanceof Officer)){
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorMessages.ACCESS_DENIED));
        }

        if (request.getLatitude() == 0. || request.getLongitude() == 0. || request.getTimestamp() == null || request.getComments().isEmpty()){
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorMessages.FILL_OBLIGATORY_FIELDS));
        }
        Risk risk = riskRepository.findById(request.getRiskId()).orElse(null);
        if (risk == null) return ResponseEntity.badRequest().body(new ErrorResponse(ErrorMessages.RISK_NOT_FOUND));

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
                .risk(risk)
                .image(Image.builder().build())
                .image(image)
                .user(user)
                .build();
        alert = alertRepository.save(alert);

        return ResponseEntity.ok(AlertPresenter.getPresenter(alert));
    }

    @Override
    public ResponseEntity<?> notify(Long id) {
        Alert alert = alertRepository.findById(id).orElse(null);
        if (alert == null) return ResponseEntity.badRequest().body(new ErrorResponse(ErrorMessages.ALERT_NOT_FOUND));

        alert.setNotified(true);
        alert = alertRepository.save(alert);

        return ResponseEntity.ok(AlertPresenter.getPresenter(alert));
    }

    @Override
    public ResponseEntity<?> getAlerts() {
        List<AlertPresenter> presenter = new ArrayList<>();
        for (Alert alert: alertRepository.findAll()) {
            presenter.add(AlertPresenter.getPresenter(alert));
        }
        return ResponseEntity.ok(presenter);
    }

    @Override
    public ResponseEntity<?> getAlert(Long id) {
        Alert alert = alertRepository.findById(id).orElse(null);
        if (alert == null) return ResponseEntity.badRequest().body(new ErrorResponse(ErrorMessages.ALERT_NOT_FOUND));
        return ResponseEntity.ok(alert);
    }

    @Override
    public ResponseEntity<?> getNotifiedAlerts() {
        List<AlertPresenter> alertPresenters = new ArrayList<>();
        Long timestamp = Tools.getDaysBefore(2);

        for (Alert alert : alertRepository.findAllByNotifiedTrueAndTimestampGreaterThanOrderByTimestampDesc(timestamp)){
            alertPresenters.add(AlertPresenter.getPresenter(alert));
        }

        return ResponseEntity.ok(alertPresenters);
    }
}