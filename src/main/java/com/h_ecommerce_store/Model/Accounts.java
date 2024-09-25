package com.h_ecommerce_store.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name="accounts")
@Getter
@Setter
public class Accounts
{
    @Id
    private String email;
    private String password;
    private String role;

    public Accounts(String email, String password, String role)
    {
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public Accounts()
    {

    }
}
