package com.h_ecommerce_store.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="shopping_carts")
@Getter
@Setter
public class Shopping_Carts
{
    @Id
    private int ID;
    private int quantity;

    @ManyToOne
    @JoinColumn (name="username")
    private Customers customer;

    @ManyToOne
    @JoinColumn(name="productID")
    private Products product;
    public Shopping_Carts()
    {

    }
    public Shopping_Carts(int quantity, Customers customer, Products product)
    {
        this.quantity = quantity;
        this.customer = customer;
        this.product = product;
    }
}
