package com.unipi.msc.smartalertapi.Response;

import com.unipi.msc.smartalertapi.Model.Alert.Alert;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlertPresenter {
    private Long Id;
    private double latitude;
    private double longitude;
    private Long timestamp;
    private Boolean notified;
    private String comments;
    private Long userId;
    private RiskPresenter risk;
    private String image;

    public static AlertPresenter getPresenter(Alert alert){
        return AlertPresenter.builder()
                .Id(alert.getId())
                .latitude(alert.getLatitude())
                .longitude(alert.getLongitude())
                .timestamp(alert.getTimestamp())
                .image("image/"+alert.getImage().getId())
                .comments(alert.getComments())
                .notified(alert.getNotified())
                .userId(alert.getUser().getId())
                .risk(RiskPresenter.getPresenter(alert.getRisk()))
                .build();
    }
}