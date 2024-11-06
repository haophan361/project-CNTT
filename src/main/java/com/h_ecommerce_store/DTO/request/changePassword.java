package com.h_ecommerce_store.DTO.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class changePassword
{
    private String username;

    @NotBlank(message = "Mật khẩu hiện tại không được để trống")
    private String oldPassword;

    @NotBlank(message = "Mật khẩu mới không được để trống")
    @Size(max = 100, message = "Số ký tự tối đa là 100")
    private String newPassword;

    @NotBlank(message = "Xác nhận mật khẩu không được để trống")
    private String confirmPassword;

    public boolean isPasswordMatching()
    {
        return newPassword.equals(confirmPassword);
    }
    public changePassword(String username)
    {
        this.username = username;
    }
}
