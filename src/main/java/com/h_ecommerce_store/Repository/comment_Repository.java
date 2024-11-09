package com.h_ecommerce_store.Repository;

import com.h_ecommerce_store.DTO.response.comment_Product;
import com.h_ecommerce_store.Entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.h_ecommerce_store.DTO.response.product_Rating;
import java.util.List;

public interface comment_Repository extends JpaRepository<Comments,Integer>
{
    @Query("SELECT COALESCE(MAX(ID),0)+1 AS newID FROM Comments ")
    int newID();

    @Query("SELECT new com.h_ecommerce_store.DTO.response.product_Rating(AVG(c.rate) ,COUNT(*)) FROM Comments c WHERE c.product.ID= :productID")
    product_Rating getRatingProduct(@Param("productID") int productID);

    @Query("SELECT COUNT(*) FROM Comments c WHERE c.product.ID= :productID AND c.rate= :rate")
    Long getNumberOfCommentsByRating(@Param("rate") int rate, @Param("productID") int productID);

    @Query("SELECT new com.h_ecommerce_store.DTO.response.comment_Product(c.comment,c.customer.cus_name,c.datetime,c.rate)  FROM Comments c WHERE c.product.ID= :productID")
    List<comment_Product> getCommentsByProduct(@Param("productID") int productID);

    @Query("SELECT COUNT(*) FROM Comments c WHERE c.customer.email= :email AND c.product.ID= :productID")
    Long getNumberOfComments(@Param("email") String email, @Param("productID") int productID);
}
