package com.unipi.msc.smartalertapi.Response;

import com.unipi.msc.smartalertapi.Model.DangerLevel;
import com.unipi.msc.smartalertapi.Model.Risk.Risk;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RiskPresenter {
    private Long id;
    private String name;
    private String dangerLevel;
    public static RiskPresenter getPresenter(Risk risk){
        return RiskPresenter.builder()
                .id(risk.getId())
                .name(risk.getName())
                .dangerLevel(risk.getDangerLevel().name())
                .build();
    }
}
