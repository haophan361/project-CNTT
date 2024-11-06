package com.h_ecommerce_store.DTO.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class revenue_Time
{
    @JsonProperty("month")
    private int month;
    @JsonProperty("revenue")
    private Double revenue;
    public revenue_Time(int month, Double revenue)
    {
        this.month = month;
        this.revenue = revenue;
    }
}
