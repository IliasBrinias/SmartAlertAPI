package com.unipi.msc.smartalertapi.Response;

import com.unipi.msc.smartalertapi.Model.User.Role;
import com.unipi.msc.smartalertapi.Model.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserPresenter {
    private Long id;
    private String username;
    private String name;
    private Role role;
    public static UserPresenter getPresenter(User u){
        return UserPresenter.builder()
                .username(u.getUsername())
                .name(u.getName())
                .role(u.getRole())
                .build();
    }
}
