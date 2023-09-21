package com.unipi.msc.smartalertapi.Response;

import com.unipi.msc.smartalertapi.Model.User.Role;
import com.unipi.msc.smartalertapi.Model.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.processing.Generated;

@Getter
@Setter
@Builder
public class LoginResponse {
    private String username;
    private String name;
    private Role role;
    private String token;
    public static LoginResponse getPresenter(User u, String token){
        return LoginResponse.builder()
                .username(u.getUsername())
                .name(u.getName())
                .role(u.getRole())
                .token(token)
                .build();
    }
}
