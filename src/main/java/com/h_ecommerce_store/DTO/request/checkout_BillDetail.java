package com.h_ecommerce_store.DTO.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Getter
@Setter
public class checkout_BillDetail
{
    private int productID;
    private String productName;
    private String image_url;
    private BigDecimal productPrice;
    private int discount;
    private BigDecimal productNewPrice;
    private int quantity;
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private String formatted_newPrice;
    public checkout_BillDetail(int productID, String productName, String image_url, BigDecimal productPrice,int discount, int quantity)
    {
        this.productID = productID;
        this.productName = productName;
        this.image_url = image_url;
        this.productPrice = productPrice;
        this.discount =discount ;
        this.quantity = quantity;
        BigDecimal discount_price = (new BigDecimal(discount).multiply(productPrice)
                .divide(new BigDecimal(100), 0, RoundingMode.FLOOR));
        this.productNewPrice=(productPrice.subtract(discount_price)).multiply(new BigDecimal(quantity));
        this.formatted_newPrice = decimalFormat.format(productNewPrice);
    }
    public checkout_BillDetail()
    {

    }
}
