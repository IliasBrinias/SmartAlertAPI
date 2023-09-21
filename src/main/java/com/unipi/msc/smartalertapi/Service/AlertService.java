package com.unipi.msc.smartalertapi.Service;

import com.unipi.msc.smartalertapi.Interface.IAlert;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AlertService implements IAlert {
    @Override
    public ResponseEntity<?> createAlert() {
        return ResponseEntity.ok().build();
    }
}
