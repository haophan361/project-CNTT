package com.h_ecommerce_store.DTO.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class revenue_productType
{
    @JsonProperty("type")
    String product_type;
    @JsonProperty("revenue")
    Double revenue;
    public revenue_productType(String product_type, Double revenue)
    {
        this.product_type = product_type;
        this.revenue = revenue;
    }
}
