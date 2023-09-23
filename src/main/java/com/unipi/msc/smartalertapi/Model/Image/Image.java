package com.unipi.msc.smartalertapi.Model.Image;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue
    private Long Id;
    private String name;
    private String type;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition="longblob")
    private byte[] imageData;
}
