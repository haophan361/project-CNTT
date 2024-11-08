package com.h_ecommerce_store.DTO.response;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

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
    private String product_type;
    private  double rate;
    private Long counting;
    private int discount;
    String formattedNewPrice;
    String formattedOldPrice;
    private final DecimalFormat decimalFormat = new DecimalFormat("#,###");
    public detail_Product(int productID, String product_name,String image_url, BigDecimal product_price,int discount, int quantity, String product_type, double rate,Long counting)
    {
        this.productID = productID;
        this.product_name = product_name;
        this.image_url = image_url;
        this.product_price = product_price;
        this.discount = discount;
        this.quantity = quantity;
        this.product_type = product_type;
        this.rate = rate;
        this.counting=counting;
        BigDecimal discount_price = (new BigDecimal(discount).multiply(product_price))
                .divide(new BigDecimal(100), 0, RoundingMode.FLOOR);
        this.new_price=product_price.subtract(discount_price);
        this.formattedNewPrice = decimalFormat.format(new_price);
        this.formattedOldPrice = decimalFormat.format(product_price);
    }
}
