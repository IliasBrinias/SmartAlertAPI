package com.unipi.msc.smartalertapi.Model.Disaster;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.unipi.msc.smartalertapi.Model.Alert.Alert;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Disaster {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToMany(mappedBy = "disaster", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Alert> alerts = new ArrayList<>();
}
