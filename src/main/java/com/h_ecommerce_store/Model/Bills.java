package com.h_ecommerce_store.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bills")
@Getter
@Setter
public class Bills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private BigDecimal cost;
    private LocalDateTime purchase_date;
    private int status;
    private String address;
    private LocalDateTime receive_date;

    @ManyToOne
    @JoinColumn(name = "cusID")
    private Customers customer;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BillDetails> billDetailsList = new ArrayList<>();

    public Bills(int ID, BigDecimal cost, LocalDateTime purchase_date, int status, String address,
                 LocalDateTime receive_date) {
        this.ID = ID;
        this.cost = cost;
        this.purchase_date = purchase_date;
        this.status = status;
        this.address = address;
        this.receive_date = receive_date;
    }

    public Bills(BigDecimal cost, LocalDateTime purchase_date, int status, String address,
                 LocalDateTime receive_date) {
        this.cost = cost;
        this.purchase_date = purchase_date;
        this.status = status;
        this.address = address;
        this.receive_date = receive_date;
    }

    public Bills() {
    }
}
