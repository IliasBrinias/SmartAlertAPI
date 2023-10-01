package com.unipi.msc.smartalertapi.Interface;

import com.unipi.msc.smartalertapi.Model.Alert.Alert;

public interface IMessage {
    void sendNotification(Alert alert);
}
