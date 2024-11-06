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
public class Bills
{
    @Id
    private int ID;
    private String cus_name;
    private BigDecimal cost;
    private LocalDateTime purchase_date;
    private int status;
    private int confirm;
    private String address;
    private String phone;
    private LocalDateTime receive_date;

    @ManyToOne
    @JoinColumn(name = "username")
    private Customers customer;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BillDetails> billDetails = new ArrayList<>();
    public Bills(Customers customer, String cus_name,String address, String phone,BigDecimal cost, int status,int confirm)
    {
        this.customer = customer;
        this.cus_name = cus_name;
        this.address = address;
        this.phone = phone;
        this.cost = cost;
        this.status = status;
        this.confirm = confirm;
        this.purchase_date = LocalDateTime.now();
    }
    public Bills ()
    {

    }
}
