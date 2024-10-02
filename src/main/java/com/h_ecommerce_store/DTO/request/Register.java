package com.h_ecommerce_store.DTO.request;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Register
{

    @NotBlank(message ="Email không được để trống")
    @Email(message="Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    @NotBlank(message = "Xác nhận mật khẩu không được để trống")
    private String  confirmPassword;

    @NotBlank(message = "Họ và Tên không được để trống")
    private String name;

    @NotBlank(message = "Tỉnh/Thành phố không được để trống")
    private String city;

    @NotBlank(message = "Quận/Huyện không được để trống")
    private String district;

    @NotBlank(message = "Phường/Xã không được để trống")
    private String ward;

    @NotBlank(message = "Tên đường/Số nhà không được để trống")
    private String houseNo;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại phải đủ 10 số")
    private String phone;
    public boolean isEmailPresent(List<String> listEmail)
    {
        if(listEmail != null && !listEmail.isEmpty())
        {
            return listEmail.contains(email);
        }
        return false;
    }
    public boolean IsPasswordMatching()
    {
         return password!=null &&password.equals(confirmPassword);
    }
}
