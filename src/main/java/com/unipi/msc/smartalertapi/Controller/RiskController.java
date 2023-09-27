package com.unipi.msc.smartalertapi.Controller;

import com.unipi.msc.smartalertapi.Interface.IRisk;
import com.unipi.msc.smartalertapi.Request.RiskRequest;
import com.unipi.msc.smartalertapi.Response.ErrorResponse;
import com.unipi.msc.smartalertapi.Shared.ErrorMessages;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("risk")
public class RiskController {
    private final IRisk iRisk;

    @GetMapping
    private ResponseEntity<?> getRisks(){
        return iRisk.getRisks();
    }
    @GetMapping("{id}")
    private ResponseEntity<?> getRisk(@PathVariable long id){
        return iRisk.getRisk(id);
    }
    @PostMapping
    private ResponseEntity<?> createRisk(@RequestBody RiskRequest request){
        return iRisk.createRisk(request);
    }
}
