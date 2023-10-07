package com.unipi.msc.smartalertapi.Service;

import com.unipi.msc.smartalertapi.Interface.IStatistics;
import com.unipi.msc.smartalertapi.Model.Alert.Alert;
import com.unipi.msc.smartalertapi.Model.Alert.AlertRepository;
import com.unipi.msc.smartalertapi.Model.Disaster.Disaster;
import com.unipi.msc.smartalertapi.Model.Disaster.DisasterRepository;
import com.unipi.msc.smartalertapi.Response.DisasterStatisticsPresenter;
import com.unipi.msc.smartalertapi.Response.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class StatisticsService implements IStatistics {
    private final DisasterRepository disasterRepository;
    private final AlertRepository alertRepository;
    @Override
    public ResponseEntity<?> getDisasterStatistics(List<Long> disasterIds, Long dateFrom, Long dateTo) {

        List<DisasterStatisticsPresenter> presenters = new ArrayList<>();
        List<Disaster> disasters = disasterRepository.findAll();
        List<Alert> alerts;
        Map<Long, Integer> mapDisasterCount = new HashMap<>();

        if (disasterIds == null){
            alerts = alertRepository.findAll();
        }else {
            alerts = alertRepository.findAllByDisasterIdIn(disasterIds);
        }

        alerts.stream().filter(alert -> {
            if (dateFrom!=0L) return alert.getTimestamp() >= dateFrom;
            return true;
        }).filter(alert -> {
            if (dateTo!=0L) return alert.getTimestamp() <= dateTo;
            return true;
        }).forEach(alert -> mapDisasterCount.merge(alert.getDisaster().getId(),1, Integer::sum));

        disasters.stream()
                .filter(disaster -> mapDisasterCount.containsKey(disaster.getId()))
                .forEach(disaster->presenters.add(DisasterStatisticsPresenter.getPresenter(disaster,mapDisasterCount.get(disaster.getId()))));

        return GenericResponse.builder().data(presenters).build().success();
    }
}
