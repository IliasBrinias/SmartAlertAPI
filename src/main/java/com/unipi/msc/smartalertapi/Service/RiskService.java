package com.unipi.msc.smartalertapi.Service;

import com.unipi.msc.smartalertapi.Interface.IRisk;
import com.unipi.msc.smartalertapi.Model.Risk.Risk;
import com.unipi.msc.smartalertapi.Model.Risk.RiskRepository;
import com.unipi.msc.smartalertapi.Request.RiskRequest;
import com.unipi.msc.smartalertapi.Response.ErrorResponse;
import com.unipi.msc.smartalertapi.Response.RiskPresenter;
import com.unipi.msc.smartalertapi.Shared.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RiskService implements IRisk {
    private final RiskRepository riskRepository;

    @Override
    public ResponseEntity<?> getRisks() {
        List<RiskPresenter> presenter = new ArrayList<>();
        for (Risk risk: riskRepository.findAll()) {
            presenter.add(RiskPresenter.getPresenter(risk));
        }
        return ResponseEntity.ok(presenter);
    }

    @Override
    public ResponseEntity<?> getRisk(long id) {
        Risk risk = riskRepository.findById(id).orElse(null);
        if (risk == null){
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorMessages.RISK_NOT_FOUND));
        }
        return ResponseEntity.ok(RiskPresenter.getPresenter(risk));
    }
    @Override
    public ResponseEntity<?> createRisk(RiskRequest request) {
        if (request.getName().isEmpty() || request.getDangerLevel() == null){
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorMessages.RISK_NOT_FOUND));
        }
        Risk risk = Risk.builder()
                .name(request.getName())
                .dangerLevel(request.getDangerLevel())
                .build();
        risk = riskRepository.save(risk);
        return ResponseEntity.ok(RiskPresenter.getPresenter(risk));
    }
}
