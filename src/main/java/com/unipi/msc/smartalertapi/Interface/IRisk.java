package com.unipi.msc.smartalertapi.Interface;

import com.unipi.msc.smartalertapi.Request.RiskRequest;
import org.springframework.http.ResponseEntity;

public interface IRisk {
    ResponseEntity<?> createRisk(RiskRequest request);

    ResponseEntity<?> getRisks();

    ResponseEntity<?> getRisk(long id);
}
