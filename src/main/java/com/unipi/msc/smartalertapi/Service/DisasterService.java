package com.unipi.msc.smartalertapi.Service;

import com.unipi.msc.smartalertapi.Interface.IDisaster;
import com.unipi.msc.smartalertapi.Model.Disaster.Disaster;
import com.unipi.msc.smartalertapi.Model.Disaster.DisasterRepository;
import com.unipi.msc.smartalertapi.Request.RiskRequest;
import com.unipi.msc.smartalertapi.Response.GenericResponse;
import com.unipi.msc.smartalertapi.Response.DisasterPresenter;
import com.unipi.msc.smartalertapi.Shared.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DisasterService implements IDisaster {
    private final DisasterRepository disasterRepository;

    @Override
    public ResponseEntity<?> getRisks() {
        List<DisasterPresenter> presenter = new ArrayList<>();
        for (Disaster disaster : disasterRepository.findAll()) {
            presenter.add(DisasterPresenter.getPresenter(disaster));
        }
        return GenericResponse.builder().data(presenter).build().success();
    }

    @Override
    public ResponseEntity<?> getRisk(long id) {
        Disaster disaster = disasterRepository.findById(id).orElse(null);
        if (disaster == null){
            return GenericResponse.builder().message(ErrorMessages.RISK_NOT_FOUND).build().badRequest();
        }
        return GenericResponse.builder().data(DisasterPresenter.getPresenter(disaster)).build().success();
    }
    @Override
    public ResponseEntity<?> createRisk(RiskRequest request) {
        if (request.getName().isEmpty()){
            return GenericResponse.builder().message(ErrorMessages.RISK_NOT_FOUND).build().badRequest();
        }
        Disaster disaster = Disaster.builder()
                .name(request.getName())
                .build();
        disaster = disasterRepository.save(disaster);
        return GenericResponse.builder().data(DisasterPresenter.getPresenter(disaster)).build().success();
    }
}
