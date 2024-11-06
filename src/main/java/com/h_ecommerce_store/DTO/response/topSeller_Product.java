package com.h_ecommerce_store.DTO.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class topSeller_Product
{
    @JsonProperty("product_name")
    private String product_name;
    @JsonProperty("revenue_product")
    Double revenue_product;
    public topSeller_Product(String product_name, Double revenue_product)
    {
        this.product_name = product_name;
        this.revenue_product = revenue_product;
    }
}
