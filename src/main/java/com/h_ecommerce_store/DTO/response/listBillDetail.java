package com.h_ecommerce_store.DTO.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
@Getter
@Setter
public class listBillDetail
{
    private String image_url;
    private String productName;
    private int productID;
    private int quantity;
    private BigDecimal price;
    private BigDecimal oldPrice;
    private String formattedPrice;
    private String formattedOldPrice;
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###");
    public listBillDetail(String image_url, String productName, int productID, int quantity, BigDecimal price, BigDecimal oldPrice)
    {
        this.image_url = image_url;
        this.productName = productName;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
        this.oldPrice = oldPrice;
        this.formattedPrice = decimalFormat.format(price);
        this.formattedOldPrice = decimalFormat.format(oldPrice);
    }
}
