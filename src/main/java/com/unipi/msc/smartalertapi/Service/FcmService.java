package com.unipi.msc.smartalertapi.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.gson.GsonBuilder;
import com.unipi.msc.smartalertapi.Interface.IMessage;
import com.unipi.msc.smartalertapi.Model.Alert.Alert;
import com.unipi.msc.smartalertapi.Response.AlertPresenter;
import com.unipi.msc.smartalertapi.Shared.Tags;
import com.unipi.msc.smartalertapi.Shared.Tools;
import org.springframework.stereotype.Component;

@Component
public class FcmService implements IMessage {
    @Override
    public void sendNotification(Alert alert){

        try {
            // Create a message with a custom topic
            Message message = Message.builder()
                    .setTopic(Tags.FIREBASE_CHANNEL)
                    .putData("alertId",String.valueOf(alert.getId()))
                    .putData("latitude",String.valueOf(alert.getLatitude()))
                    .putData("longitude",String.valueOf(alert.getLongitude()))
                    .putData("disaster",String.valueOf(alert.getDisaster().getName()))
                    .putData("help", Tools.getInfo(alert.getDisaster().getName()))
                    .build();

            // Send the message
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
