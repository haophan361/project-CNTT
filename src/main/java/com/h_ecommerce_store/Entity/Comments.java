package com.h_ecommerce_store.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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
    private Users user;
    public Comments()
    {

    }
    public Comments(String comment, int rate, Products product, Users user)
    {
        this.comment = comment;
        this.rate = rate;
        this.product = product;
        this.user = user;
    }
}
