package com.h_ecommerce_store.Model;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String cus_name;
    private String email;
    private String address;
    private String phone;
    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    private List<Bills> billsList=new ArrayList<>();
    @OneToMany(mappedBy = "customer",fetch = FetchType.EAGER)
    private List<Comments> commentsList=new ArrayList<>();
    public Customers(int ID, String cus_name, String email, String address, String phone)
    {
        this.ID = ID;
        this.cus_name = cus_name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public Customers(String cus_name, String email, String address, String phone)
    {
        this.cus_name = cus_name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public Customers()
    {

    }
}
