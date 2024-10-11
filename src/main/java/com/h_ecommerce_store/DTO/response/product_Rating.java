package com.h_ecommerce_store.DTO.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class product_Rating
{
    private Long counting;
    private double rate;
    public product_Rating(Double rate, Long counting)
    {
        if(rate!=null && counting!=null)
        {
            this.rate = rate;
            this.counting = counting;
        }
        else
        {
            this.rate = 0.0;
            this.counting=0L;
        }
    }
}
