package com.example.billboardproject.model;


import lombok.*;
import javax.persistence.Entity;

@Entity
@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class City extends BaseEntity{
    private String name;
}
