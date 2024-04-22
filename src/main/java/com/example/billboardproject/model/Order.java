package com.example.billboardproject.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name = "OrderForBillboards")
public class Order extends BaseEntity{
    private String startDate;
    private String endDate;
    private String telNumber;
    private int status; // 0-Waiting 1-Approved 2-Rejected

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Billboard billboard;
}