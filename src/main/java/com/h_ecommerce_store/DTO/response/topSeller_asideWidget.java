package com.h_ecommerce_store.DTO.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Getter
@Setter

public class topSeller_asideWidget
{
    private int ID;
    private String name;
    private String image_url;
    private String type;
    private BigDecimal cost;
    private BigDecimal new_cost;
    private int discount;
    private String formattedNewCost;
    private String formattedOldCost;
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###");
    public topSeller_asideWidget(int ID, String name, String image_url, String type, BigDecimal cost, int discount)
    {
        this.ID = ID;
        this.name = name;
        this.image_url = image_url;
        this.type = type;
        this.cost = cost;
        this.discount = discount;
        BigDecimal discount_price = (new BigDecimal(discount).multiply(cost))
                .divide(new BigDecimal(100), 0, RoundingMode.FLOOR);
        this.new_cost=cost.subtract(discount_price);
        this.formattedNewCost = decimalFormat.format(new_cost);
        this.formattedOldCost = decimalFormat.format(cost);
    }
}
