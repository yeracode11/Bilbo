package com.example.billboardproject.model;


import lombok.*;
import javax.persistence.Entity;

@Entity
@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Location extends BaseEntity{
    private String name;
    private Long city_id;
}
