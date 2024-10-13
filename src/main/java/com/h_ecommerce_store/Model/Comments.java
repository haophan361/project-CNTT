package com.h_ecommerce_store.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name="comments")
@Getter
@Setter
public class Comments
{
    @Id
    private int ID;
    private String comment;
    private int rate;
    @CreationTimestamp
    private LocalDateTime datetime;
    @ManyToOne
    @JoinColumn(name="productID")
    private Products product;

    @ManyToOne
    @JoinColumn(name="username")
    private Customers customer;
    public Comments()
    {

    }
    public Comments(String comment, int rate, Products product, Customers customer)
    {
        this.comment = comment;
        this.rate = rate;
        this.product = product;
        this.customer = customer;
    }
}
