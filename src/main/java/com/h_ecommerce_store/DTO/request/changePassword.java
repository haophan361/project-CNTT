package com.h_ecommerce_store.DTO.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class changePassword
{
    @NotBlank(message ="Email không được để trống")
    @Email(message="Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Mật khẩu hiện tại không được để trống")
    private String oldPassword;

    @NotBlank(message = "Mật khẩu mới không được để trống")
    private String newPassword;

    @NotBlank(message = "Xác nhận mật khẩu không được để trống")
    private String confirmPassword;

    public boolean isPasswordMatching()
    {
        return newPassword.equals(confirmPassword);
    }
    public boolean checkPassword(String password)
    {
        return oldPassword.equals(password);
    }
}
