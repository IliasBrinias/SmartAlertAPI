package com.unipi.msc.smartalertapi.Service;

import com.unipi.msc.smartalertapi.Interface.IAuth;
import com.unipi.msc.smartalertapi.Model.User.*;
import com.unipi.msc.smartalertapi.Request.LoginRequest;
import com.unipi.msc.smartalertapi.Request.RegisterRequest;
import com.unipi.msc.smartalertapi.Response.ErrorResponse;
import com.unipi.msc.smartalertapi.Response.LoginResponse;
import com.unipi.msc.smartalertapi.Response.UserPresenter;
import com.unipi.msc.smartalertapi.Shared.ErrorMessages;
import com.unipi.msc.smartalertapi.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthService implements IAuth {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<?> register(RegisterRequest request) {
        if (request.getUsername().isEmpty() || request.getPassword().isEmpty() || request.getRole() == null){
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorMessages.FILL_OBLIGATORY_FIELDS));
        }
        if (userRepository.existsByUsername(request.getUsername())){
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorMessages.USERNAME_EXISTS));
        }
        User user;
        if (request.getRole() == Role.CITIZEN){
            user = new Citizen(request.getUsername(), passwordEncoder.encode(request.getPassword()), request.getName());
        } else if (request.getRole() == Role.OFFICER) {
            user = new Officer(request.getUsername(), passwordEncoder.encode(request.getPassword()), request.getName());
        }else {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorMessages.FILL_OBLIGATORY_FIELDS));
        }
        user = userRepository.save(user);
        return ResponseEntity.ok(LoginResponse.getPresenter(user,jwtService.generateToken(user)));
    }

    @Override
    public ResponseEntity<?> login(LoginRequest request) {
        if (request.getUsername().isEmpty() || request.getPassword().isEmpty()){
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorMessages.FILL_OBLIGATORY_FIELDS));
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (user == null) return  ResponseEntity.notFound().build();
        return ResponseEntity.ok(LoginResponse.getPresenter(user, jwtService.generateToken(user)));
    }
}
