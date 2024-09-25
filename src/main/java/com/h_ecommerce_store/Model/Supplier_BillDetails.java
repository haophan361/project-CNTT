package com.h_ecommerce_store.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name="supplier_billdetails")
@Getter
@Setter
public class Supplier_BillDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private BigDecimal cost;
    private int quantity;
    @ManyToOne
    @JoinColumn(name="productID")
    private Products product;

    @ManyToOne
    @JoinColumn(name="supplierbillID")
    private Supplier_Bills supplier_bill;
    public Supplier_BillDetails(int ID,BigDecimal cost, int quantity)
    {
        this.ID = ID;
        this.cost = cost;
        this.quantity = quantity;
    }

    public Supplier_BillDetails(BigDecimal cost, int quantity)
    {

        this.cost = cost;
        this.quantity = quantity;
    }
    public  Supplier_BillDetails()
    {

    }
}
