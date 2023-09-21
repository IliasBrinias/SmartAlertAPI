package com.unipi.msc.smartalertapi.Model.User;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "OFFICER")
public class Officer extends User{
    public Officer(String username, String password, String name) {
        super(username, password, name, Role.OFFICER);
    }

    public Officer() {
    }
}
