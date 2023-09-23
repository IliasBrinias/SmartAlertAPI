package com.unipi.msc.smartalertapi.Controller;

import com.unipi.msc.smartalertapi.Interface.IImage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("image")
@RequiredArgsConstructor
public class ImageController {
    private final IImage iImage;
    @GetMapping("{imageId}")
    public ResponseEntity<?> getImage(@PathVariable Long imageId){
        return iImage.getImage(imageId);
    }
}
