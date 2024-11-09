package com.h_ecommerce_store.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="customers")
@Getter
@Setter
public class Customers
{
    @Id
    private String email;
    private String cus_name;
    private String address;
    private String phone;
    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    private List<Bills> billsList=new ArrayList<>();
    @OneToMany(mappedBy = "customer",fetch = FetchType.EAGER)
    private List<Comments> commentsList=new ArrayList<>();
    @OneToMany(mappedBy = "customer",fetch =FetchType.LAZY)
    private List<Shopping_Carts> cartsList=new ArrayList<>();
    public Customers()
    {

    }
    public Customers(String cus_name, String email, String address, String phone)
    {
        this.cus_name=cus_name;
        this.email=email;
        this.address=address;
        this.phone=phone;
    }
}
