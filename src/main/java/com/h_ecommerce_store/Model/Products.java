package com.h_ecommerce_store.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="products")
@Getter
@Setter

public class Products
{
    @Id
    private int ID;
    private String product_name;
    private String product_type;
    private String brand;
    private BigDecimal cost;
    private String detail;
    private int quantity;
    private String image_url;
    private int discount;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comments> commentsList=new ArrayList<>();
    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY)
    private List<BillDetails> billsdetailList=new ArrayList<>();
    public Products(int ID, String product_name, String product_type, String brand,
                    BigDecimal cost, int quantity, String image_url, int discount) {
        this.ID = ID;
        this.product_name = product_name;
        this.product_type= product_type;
        this.brand = brand;
        this.cost = cost;
        this.quantity = quantity;
        this.image_url = image_url;
        this.discount = discount;
    }

    public Products(String product_name, String product_type, String brand, BigDecimal cost, int quantity,
                              String image_url, int discount) {
        this.product_name = product_name;
        this.product_type=product_type;
        this.brand = brand;
        this.cost = cost;
        this.quantity = quantity;
        this.image_url = image_url;
        this.discount = discount;
    }
    public Products()
    {

    }
}
