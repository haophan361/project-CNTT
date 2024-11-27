package com.h_ecommerce_store.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class checkout_Bill
{
    @NotBlank(message = "Họ và tên không được để trống")
    private String name;
    @NotBlank(message = "Tỉnh/Thành phố không được để trống")
    private String city;
    @NotBlank(message = "Quận/Huyện không được để trống")
    private String district;

    private String ward;

    private String houseNo;
    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại phải đủ 10 số")
    private String phone;
    private List<checkout_BillDetail> billDetails;
    @NotNull(message = "Hãy chọn phương thức thanh toán")
    private Integer status;
    private BigDecimal total;
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private String formatted_price;
    public checkout_Bill()
    {
        
    }
    public checkout_Bill(List<checkout_BillDetail> billDetails)
    {
        this.total=new BigDecimal(0);
        if(billDetails==null)
        {
            this.billDetails = new ArrayList<>();
        }
        else
        {
            this.billDetails = billDetails;
            for(checkout_BillDetail billDetail : billDetails)
            {
                this.total=this.total.add(billDetail.getProductNewPrice());
            }
        }
        this.formatted_price = decimalFormat.format(total);
    }
}
