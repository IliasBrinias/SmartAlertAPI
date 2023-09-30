package com.unipi.msc.smartalertapi.Response;

import com.unipi.msc.smartalertapi.Model.Disaster.Disaster;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DisasterPresenter {
    private Long id;
    private String name;
    public static DisasterPresenter getPresenter(Disaster disaster){
        return DisasterPresenter.builder()
                .id(disaster.getId())
                .name(disaster.getName())
                .build();
    }
}
