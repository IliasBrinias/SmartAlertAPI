package com.unipi.msc.smartalertapi.Interface;

import org.springframework.http.ResponseEntity;

public interface IImage {
    ResponseEntity<?> getImage(Long imageId);
}
