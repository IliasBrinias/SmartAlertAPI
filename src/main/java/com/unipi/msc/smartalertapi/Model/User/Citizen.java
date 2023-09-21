package com.unipi.msc.smartalertapi.Model.User;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "CITIZEN")
public class Citizen extends User{
    public Citizen(String username, String password, String name) {
        super(username, password, name, Role.CITIZEN);
    }

    public Citizen() {

    }
}
