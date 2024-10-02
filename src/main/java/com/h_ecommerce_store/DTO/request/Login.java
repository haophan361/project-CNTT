package com.h_ecommerce_store.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login
{
    @NotBlank(message ="Email không được để trống")
    @Email(message="Email không đúng định dạng")
    private String email;
    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;
    public Login()
    {

    }
}
