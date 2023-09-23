package com.unipi.msc.smartalertapi.Interface;

import com.unipi.msc.smartalertapi.Request.AlertRequest;
import org.springframework.http.ResponseEntity;

public interface IAlert {
    ResponseEntity<?> createAlert(AlertRequest request);

    ResponseEntity<?> notify(Long id);

    ResponseEntity<?> getAlerts();

    ResponseEntity<?> getAlert(Long id);

    ResponseEntity<?> getNotifiedAlerts();
}
