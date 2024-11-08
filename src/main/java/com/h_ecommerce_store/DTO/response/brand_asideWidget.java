package com.h_ecommerce_store.DTO.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class brand_asideWidget
{
    String brand;
    long count;
    public brand_asideWidget(String brand, long count)
    {
        this.brand = brand;
        this.count = count;
    }
}
