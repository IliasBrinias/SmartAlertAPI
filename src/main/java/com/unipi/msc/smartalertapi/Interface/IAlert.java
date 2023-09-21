package com.unipi.msc.smartalertapi.Interface;

import org.springframework.http.ResponseEntity;

public interface IAlert {
    ResponseEntity<?> createAlert();
}
