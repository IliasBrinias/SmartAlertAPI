package com.unipi.msc.smartalertapi.Response;

import com.unipi.msc.smartalertapi.Model.Disaster.Disaster;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DisasterStatisticsPresenter {
    private String disaster;
    private int count;
    public static DisasterStatisticsPresenter getPresenter(Disaster disaster, Integer count){
        return  DisasterStatisticsPresenter.builder()
                .disaster(disaster.getName())
                .count(count)
                .build();
    }
}
