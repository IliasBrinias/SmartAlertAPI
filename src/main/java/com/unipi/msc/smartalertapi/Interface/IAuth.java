package com.unipi.msc.smartalertapi.Interface;

import com.unipi.msc.smartalertapi.Request.LoginRequest;
import com.unipi.msc.smartalertapi.Request.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface IAuth {
    ResponseEntity<?> register(RegisterRequest request);
    ResponseEntity<?> login(LoginRequest request);
}
