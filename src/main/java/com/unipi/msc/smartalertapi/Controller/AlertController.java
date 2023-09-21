package com.unipi.msc.smartalertapi.Controller;

import com.unipi.msc.smartalertapi.Interface.IAlert;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("alert")
public class AlertController {
    private final IAlert iAlert;
    @PostMapping
    private ResponseEntity<?> createAlert(){
        return iAlert.createAlert();
    }
}
