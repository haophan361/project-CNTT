package com.h_ecommerce_store.Service;

import com.h_ecommerce_store.Model.Shopping_Carts;
import com.h_ecommerce_store.Repository.Cart_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class cart_Service
{
    @Autowired
    Cart_Repository cart_repository;
    List<Shopping_Carts> getCart_Customer(String username)
    {
        return cart_repository.getShopping_CartsByCustomer(username);
    }
}
