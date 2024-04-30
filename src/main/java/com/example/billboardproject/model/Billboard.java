package com.example.billboardproject.model;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Billboard extends BaseEntity{
    private String billboard_url;
    private Long location_id;
    private Long city_id;
    private String type;
    private String size;
    private boolean isHasLightning;
    private int price;
    private boolean isActive;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
