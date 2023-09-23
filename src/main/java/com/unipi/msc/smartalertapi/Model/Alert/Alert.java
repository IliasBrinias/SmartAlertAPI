package com.unipi.msc.smartalertapi.Model.Alert;

import com.unipi.msc.smartalertapi.Model.Image.Image;
import com.unipi.msc.smartalertapi.Model.Risk.Risk;
import com.unipi.msc.smartalertapi.Model.User.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Alert {
    @Id
    @GeneratedValue
    private Long Id;
    private double latitude;
    private double longitude;
    private Long timestamp;
    private String comments;
    private Boolean notified;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "risk_id")
    private Risk risk;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Image image;
}
