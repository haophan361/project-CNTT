package com.h_ecommerce_store.DTO.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class updateProfile
{
    private String username;
    @NotBlank(message = "Họ và Tên không được để trống")
    @Size(max = 300, message = "Số ký tự tối đa là 300")
    private String name;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại phải đủ 10 số")
    private String phone;
    public updateProfile(String name, String username, String phone)
    {
        this.name = name;
        this.username = username;
        this.phone = phone;
    }
}
