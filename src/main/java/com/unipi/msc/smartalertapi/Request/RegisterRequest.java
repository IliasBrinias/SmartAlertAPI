package com.unipi.msc.smartalertapi.Request;

import com.unipi.msc.smartalertapi.Model.User.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private String name;
    private Role role;
}
