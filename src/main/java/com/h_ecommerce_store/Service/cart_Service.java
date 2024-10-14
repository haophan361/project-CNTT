package com.h_ecommerce_store.Service;

import com.h_ecommerce_store.DTO.response.list_ShoppingCart;
import com.h_ecommerce_store.Model.Shopping_Carts;
import com.h_ecommerce_store.Repository.Cart_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class cart_Service
{
    @Autowired
    Cart_Repository cart_repository;
    public List<list_ShoppingCart> getCart_Customer(String username)
    {
        List<Shopping_Carts> shoppingCartsList= cart_repository.getShopping_CartsByCustomer(username);
        List<list_ShoppingCart> list_Cart=new ArrayList<>();
        BigDecimal total=new BigDecimal(0);
        for(Shopping_Carts cart :shoppingCartsList)
        {
            list_ShoppingCart Cart=new list_ShoppingCart(cart.getProduct().getProduct_name(),
                    cart.getQuantity(),cart.getProduct().getCost(),cart.getProduct().getDiscount(),cart.getProduct().getImage_url());
            list_Cart.add(Cart);
        }
        return list_Cart;
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
