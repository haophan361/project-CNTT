package com.h_ecommerce_store.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
public class Users
{
    @Id
    private String email;
    private String name;
    private String address;
    private String phone;
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Bills> billsList=new ArrayList<>();
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<Comments> commentsList=new ArrayList<>();
    @OneToMany(mappedBy = "user",fetch =FetchType.LAZY)
    private List<Shopping_Carts> cartsList=new ArrayList<>();
    public Users()
    {

    }
    public Users(String name, String email, String address, String phone)
    {
        this.name=name;
        this.email=email;
        this.address=address;
        this.phone=phone;
    }
}
