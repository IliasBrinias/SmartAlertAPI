package com.unipi.msc.smartalertapi.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.gson.GsonBuilder;
import com.unipi.msc.smartalertapi.Interface.IMessage;
import com.unipi.msc.smartalertapi.Model.Alert.Alert;
import com.unipi.msc.smartalertapi.Model.DangerLevel;
import com.unipi.msc.smartalertapi.Model.User.User;
import com.unipi.msc.smartalertapi.Response.AlertPresenter;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class FcmService implements IMessage {
    private final RestTemplate restTemplate;
    private final static String postUrl = "https://fcm.googleapis.com/fcm/send";
    private final static String fcmServerKey ="UzFjI7ciWJJvZG7Hhsmx0nSiU5NDaI8W1JWeOoEJEpo";

    public FcmService() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void sendNotification(Alert alert){

        try {
            // Create a message with a custom topic
            Message message = Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle("Danger")
                            .setBody(new GsonBuilder().setPrettyPrinting().create().toJson(AlertPresenter.getPresenter(alert)))
                            .setImage(null)
                            .build())
                    .setTopic("DANGER_TOPIC")
                    .putData("alertId",String.valueOf(alert.getId()))
                    .putData("disaster",String.valueOf(alert.getDisaster().getName()))
                    .putData("help",getInfo(alert.getDisaster().getName()))
                    .build();

            // Send the message
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }
    private String getInfo(String disasterName){
        return "";
    }
}
