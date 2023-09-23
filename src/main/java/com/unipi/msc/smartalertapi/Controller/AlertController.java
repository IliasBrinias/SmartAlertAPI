package com.unipi.msc.smartalertapi.Controller;

import com.unipi.msc.smartalertapi.Interface.IAlert;
import com.unipi.msc.smartalertapi.Request.AlertRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("alert")
public class AlertController {
    private final IAlert iAlert;

    @GetMapping
    private ResponseEntity<?> getAlerts(){
        return iAlert.getAlerts();
    }
    @GetMapping("{id}")
    private ResponseEntity<?> getAlert(@PathVariable Long id){
        return iAlert.getAlert(id);
    }

    @PostMapping
    private ResponseEntity<?> createAlert(@ModelAttribute AlertRequest request){
        return iAlert.createAlert(request);
    }

    @PostMapping("{id}/notify")
    private ResponseEntity<?> notify(@PathVariable int id){
        return iAlert.notify(id);
    }

}
