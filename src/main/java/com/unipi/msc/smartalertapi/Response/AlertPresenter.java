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
    private String username;
    private String dangerLevel;
    private DisasterPresenter disaster;
    private String image;

    public static AlertPresenter getPresenter(Alert alert){
        String imagePath = null;
        try {
            imagePath = "image/"+alert.getImage().getId();
        } catch (Exception ignore){}

        return AlertPresenter.builder()
                .Id(alert.getId())
                .latitude(alert.getLatitude())
                .longitude(alert.getLongitude())
                .timestamp(alert.getTimestamp())
                .image(imagePath)
                .comments(alert.getComments())
                .notified(alert.getNotified())
                .username(alert.getUser().getName())
                .disaster(DisasterPresenter.getPresenter(alert.getDisaster()))
                .dangerLevel(alert.getDangerLevel().name())
                .build();
    }
}