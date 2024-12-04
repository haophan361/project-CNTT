package com.h_ecommerce_store.Service;

import com.h_ecommerce_store.DTO.response.list_ShoppingCart;
import com.h_ecommerce_store.Entity.Shopping_Carts;
import com.h_ecommerce_store.Repository.Cart_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class cart_Service
{
    private final Cart_Repository cart_repository;
    public Shopping_Carts checkExist_Cart(int productID, String username) {
        return cart_repository.findByProduct_IDAndUser_Email(productID, username);
    }
    public List<Shopping_Carts> getCart_Customer(String username)
    {
        return cart_repository.getShopping_CartsByCus(username);
    }
    public List<list_ShoppingCart> getCart_Customer(List<Shopping_Carts> shopping_carts)
    {
        List<list_ShoppingCart> list_Cart=new ArrayList<>();
        for(Shopping_Carts cart :shopping_carts)
        {
            list_ShoppingCart Cart=new list_ShoppingCart(cart.getID(),
                    cart.getProduct().getID(),
                    cart.getProduct().getProduct_name(),
                    cart.getProduct().getQuantity(),
                    cart.getQuantity(),
                    cart.getProduct().getCost(),
                    cart.getProduct().getDiscount(),
                    cart.getProduct().getImage_url());
            list_Cart.add(Cart);
        }
        return list_Cart;
    }
    public Long getCartCountByCus(String username)
    {
        return cart_repository.getCartCount(username);
    }
    public void addToCart(Shopping_Carts cart)
    {
        Shopping_Carts existCart=cart_repository.checkExist_Cart(cart.getProduct().getID(),cart.getUser().getEmail());
        if(existCart==null)
        {
            int ID=cart_repository.newID();
            cart.setID(ID);
            cart_repository.save(cart);
        }
        else
        {
            existCart.setQuantity(cart.getQuantity() + existCart.getQuantity());
            cart_repository.save(existCart);
        }
    }
    public Shopping_Carts getCartByproduct_account(int productID,String username)
    {
        Shopping_Carts cart;
        cart=cart_repository.checkExist_Cart(productID,username);
        return cart;
    }
    public int getCartID(int productID,String username)
    {
        return cart_repository.getCartID(productID,username);
    }
    public void updateCart(Shopping_Carts cart)
    {
        Optional<Shopping_Carts> existCart=cart_repository.findById(cart.getID());
        if(existCart.isPresent())
        {
            existCart.get().setQuantity(cart.getQuantity());
            cart_repository.save(existCart.get());
        }
    }
    public void deleteCart(int cartID)
    {
        cart_repository.deleteById(cartID);
    }
    public Shopping_Carts getCart(int cartID)
    {
        Optional<Shopping_Carts> cart= cart_repository.findById(cartID);
        return cart.orElse(null);
    }
    public Long totalCart()
    {
        return cart_repository.count();
    }
}
