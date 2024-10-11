package com.h_ecommerce_store.DTO.response;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class detail_Product
{
    private int productID;
    private String product_name;
    private String image_url;
    private BigDecimal product_price;
    private int quantity;
    private BigDecimal new_price;
    private String description;
    private  double rate;
    private Long counting;
    public detail_Product(int productID, String product_name,String image_url, BigDecimal product_price, int quantity, BigDecimal new_price, String description, double rate,Long counting)
    {
        this.productID = productID;
        this.product_name = product_name;
        this.image_url = image_url;
        this.product_price = product_price;
        this.quantity = quantity;
        this.new_price = new_price;
        this.description = description;
        this.rate = rate;
        this.counting=counting;
    }
}
