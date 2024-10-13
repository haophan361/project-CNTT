package com.h_ecommerce_store.Repository;

import com.h_ecommerce_store.Model.Shopping_Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Cart_Repository extends JpaRepository<Shopping_Carts, Integer>
{
    @Query("SELECT c FROM Shopping_Carts c WHERE c.customer.email= :username")
    public List<Shopping_Carts> getShopping_CartsByCustomer(@Param("username") String username);
}
