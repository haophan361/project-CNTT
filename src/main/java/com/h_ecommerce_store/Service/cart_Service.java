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
    public List<Shopping_Carts> getCart_Customer(String username)
    {
        return cart_repository.getShopping_CartsByCustomer(username);
    }
    public Shopping_Carts addToCart(Shopping_Carts cart)
    {
        Shopping_Carts existCart=cart_repository.checkExist_Cart(cart.getProduct().getID(),cart.getCustomer().getEmail());
        if(existCart==null)
        {
            int ID=cart_repository.newID();
            cart.setID(ID);
            return cart_repository.save(cart);
        }
        else
        {
            existCart.setQuantity(cart.getQuantity() + existCart.getQuantity());
            return cart_repository.save(existCart);
        }
    }
}
