package com.h_ecommerce_store.DTO.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class productType_asideWidget
{
    String product_type;
    long count;
    public productType_asideWidget(String product_type, long count)
    {
        this.product_type = product_type;
        this.count = count;
    }
}
