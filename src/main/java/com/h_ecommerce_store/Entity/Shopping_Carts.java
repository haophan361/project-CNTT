package com.h_ecommerce_store.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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
    private Users user;

    @ManyToOne
    @JoinColumn(name="productID")
    private Products product;
    public Shopping_Carts()
    {

    }
    public Shopping_Carts(int ID, int quantity, Users user, Products product)
    {
        this.ID=ID;
        this.quantity = quantity;
        this.user = user;
        this.product = product;
    }
    public Shopping_Carts(int quantity, Users user, Products product)
    {
        this.quantity = quantity;
        this.user = user;
        this.product = product;
    }
}
