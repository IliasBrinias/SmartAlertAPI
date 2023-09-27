package com.unipi.msc.smartalertapi.Request;

import com.unipi.msc.smartalertapi.Model.DangerLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RiskRequest {
    private DangerLevel dangerLevel;
    private String name;
}
