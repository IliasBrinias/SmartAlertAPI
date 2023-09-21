package com.unipi.msc.smartalertapi.Controller;

import com.unipi.msc.smartalertapi.Interface.IAuth;
import com.unipi.msc.smartalertapi.Request.LoginRequest;
import com.unipi.msc.smartalertapi.Request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final IAuth auth;
    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        return auth.register(request);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        return auth.login(request);
    }
}
