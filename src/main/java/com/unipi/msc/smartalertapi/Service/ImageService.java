package com.unipi.msc.smartalertapi.Service;

import com.unipi.msc.smartalertapi.Interface.IImage;
import com.unipi.msc.smartalertapi.Model.Image.Image;
import com.unipi.msc.smartalertapi.Model.Image.ImageRepository;
import com.unipi.msc.smartalertapi.Response.ErrorResponse;
import com.unipi.msc.smartalertapi.Shared.ErrorMessages;
import com.unipi.msc.smartalertapi.Shared.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageService implements IImage {
    private final ImageRepository imageRepository;
    @Override
    public ResponseEntity<?> getImage(Long imageId) {
        Image image = imageRepository.findById(imageId).orElse(null);
        if (image==null) return ResponseEntity.badRequest().body(new ErrorResponse(ErrorMessages.IMAGE_NOT_FOUND));
        byte[] img = ImageUtils.decompressImage(image.getImageData());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(img);
    }
}
