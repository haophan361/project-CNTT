package com.h_ecommerce_store.DTO.response;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class manage_User
{
    private String email;
    private String name;
    private String address;
    private String phone;
    private String role;
    private String name_role;
    public manage_User(String email, String name, String address, String phone, String role)
    {
        this.email = email;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.role = role;
        if(role.equals("ROLE_ADMIN"))
        {
            this.name_role="ADMIN";
        }
        if(role.equals("ROLE_USER"))
        {
            this.name_role="Khách hàng";
        }
        if(role.equals("ROLE_STAFF"))
        {
            this.name_role="Nhân viên";
        }
    }
}
