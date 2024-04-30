package com.example.billboardproject.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name = "order_for_billboards")
public class Order extends BaseEntity{
    private String startDate;
    private String endDate;
    private String orderDate;
    private String telNumber;
    private int status; // 0-Waiting 1-Approved 2-Rejected

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Billboard billboard;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}
