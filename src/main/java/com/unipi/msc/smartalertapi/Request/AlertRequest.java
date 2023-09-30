package com.unipi.msc.smartalertapi.Request;

import com.unipi.msc.smartalertapi.Model.DangerLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlertRequest {
    private double latitude;
    private double longitude;
    private Long timestamp;
    private String comments;
    private Long disasterId;
    private DangerLevel dangerLevel;
    private MultipartFile image;
}
