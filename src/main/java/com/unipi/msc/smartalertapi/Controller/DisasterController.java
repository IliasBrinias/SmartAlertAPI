package com.unipi.msc.smartalertapi.Controller;

import com.unipi.msc.smartalertapi.Interface.IDisaster;
import com.unipi.msc.smartalertapi.Request.RiskRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("disaster")
public class DisasterController {
    private final IDisaster iDisaster;

    @GetMapping
    private ResponseEntity<?> getRisks(){
        return iDisaster.getRisks();
    }
    @GetMapping("{id}")
    private ResponseEntity<?> getRisk(@PathVariable long id){
        return iDisaster.getRisk(id);
    }
    @PostMapping
    private ResponseEntity<?> createRisk(@RequestBody RiskRequest request){
        return iDisaster.createRisk(request);
    }
}
