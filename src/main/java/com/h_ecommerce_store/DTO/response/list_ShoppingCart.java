package com.h_ecommerce_store.DTO.response;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Getter
@Setter
public class list_ShoppingCart
{
    private String productName;
    private int quantity;
    private BigDecimal price;
    private int discount;
    private BigDecimal new_price;
    private String formatted_newPrice;
    private String formatted_price;
    private String image_url;
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###");
    public list_ShoppingCart(String productName, int quantity, BigDecimal price, int discount,String image_url)
    {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.image_url = image_url;
        BigDecimal discount_price = (new BigDecimal(discount).multiply(price)
                .divide(new BigDecimal(100), 0, RoundingMode.FLOOR));
        this.new_price=(price.subtract(discount_price)).multiply(new BigDecimal(quantity));
        this.formatted_price=decimalFormat.format(new_price);
        this.formatted_newPrice=decimalFormat.format(new_price);
    }
}
