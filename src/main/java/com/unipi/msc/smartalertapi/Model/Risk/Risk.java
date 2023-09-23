package com.unipi.msc.smartalertapi.Model.Risk;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.unipi.msc.smartalertapi.Model.Alert.Alert;
import com.unipi.msc.smartalertapi.Model.DangerLevel;
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
public class Risk {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private DangerLevel dangerLevel;
    @OneToMany(mappedBy = "risk", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Alert> alerts = new ArrayList<>();
}
