package com.h_ecommerce_store.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name="comments")
@Getter
@Setter
public class Comments
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String comment;
    private BigDecimal rate;
    @ManyToOne
    @JoinColumn(name="productID")
    private Products product;

    @ManyToOne
    @JoinColumn(name="cusID")
    private Customers customer;

    public Comments(int ID, String comment, BigDecimal rate, Products product, Customers customer)
    {
        this.ID = ID;
        this.comment = comment;
        this.rate = rate;
        this.product = product;
        this.customer = customer;
    }
    public Comments(String comment, BigDecimal rate, Products product, Customers customer)
    {
        this.comment = comment;
        this.rate = rate;
        this.product = product;
        this.customer = customer;
    }
    public Comments()
    {

    }
}
