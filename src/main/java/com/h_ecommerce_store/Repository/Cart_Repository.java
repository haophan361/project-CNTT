package com.h_ecommerce_store.Repository;

import com.h_ecommerce_store.Model.Shopping_Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Cart_Repository extends JpaRepository<Shopping_Carts, Integer>
{
    @Query("SELECT COALESCE(MAX(ID),0)+1 AS newID FROM Shopping_Carts ")
    int newID();
    @Query("SELECT c FROM Shopping_Carts c WHERE c.customer.email= :username")
    List<Shopping_Carts> getShopping_CartsByCustomer(@Param("username") String username);
    @Query("SELECT c FROM Shopping_Carts c WHERE c.product.ID= :productID and c.customer.email= :username")
    Shopping_Carts checkExist_Cart(@Param("productID") int productID, @Param("username") String username);
    @Query("SELECT c.ID FROM Shopping_Carts c WHERE c.product.ID= :productID and c.customer.email= :username")
    int getCartID(@Param("productID") int productID, @Param("username") String username);
}
