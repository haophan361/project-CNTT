package com.h_ecommerce_store.DTO.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class product_Type
{
    private long number;
    private String product_type;

    public product_Type(long number, String product_type)
    {
        this.number = number;
        this.product_type = product_type;
    }
}
