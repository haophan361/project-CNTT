package com.h_ecommerce_store.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "billdetails")
@Getter
@Setter
public class BillDetails {
    @Id
    private int ID;
    private BigDecimal cost;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "billID")
    private Bills bill;

    @ManyToOne
    @JoinColumn(name = "proID")
    private Products product;

    public BillDetails(Products product,BigDecimal cost, int quantity)
    {
        this.product=product;
        this.cost = cost;
        this.quantity = quantity;
    }

    public BillDetails() {
    }
}
