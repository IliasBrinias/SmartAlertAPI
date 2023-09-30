package com.unipi.msc.smartalertapi.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Builder
@Getter
@Setter
public class GenericResponse<T> {
    private int code;
    private String message;
    private T data;

    public ResponseEntity<?> success(){
        this.code = 200;
        return ResponseEntity.ok(this);
    }

    public ResponseEntity<?> badRequest(){
        this.code = 400;
        return ResponseEntity.badRequest().body(this);
    }
    public ResponseEntity<?> response(){
        return ResponseEntity.badRequest().body(this);
    }

}
