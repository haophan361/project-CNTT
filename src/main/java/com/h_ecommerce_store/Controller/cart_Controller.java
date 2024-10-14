package com.h_ecommerce_store.Controller;

import com.h_ecommerce_store.Model.Customers;
import com.h_ecommerce_store.Model.Products;
import com.h_ecommerce_store.Model.Shopping_Carts;
import com.h_ecommerce_store.Service.account_Service;
import com.h_ecommerce_store.Service.cart_Service;
import com.h_ecommerce_store.Service.customer_Service;
import com.h_ecommerce_store.Service.product_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Controller
public class cart_Controller
{
    @Autowired
    cart_Service cart_service;
    @Autowired
    product_Service product_service;
    @Autowired
    account_Service account_service;
    @Autowired
    customer_Service customer_service;
    @PostMapping("/user/addToCart/{productID}/{quantity}")
    public String addToCart(@PathVariable("productID") int productID, @PathVariable("quantity") int quantity)
    {
        Products product=product_service.getProductsByID(productID);
        String username=account_service.getLoggedUserName();
        Customers customer=customer_service.getCustomer(username);
        Shopping_Carts cart=new Shopping_Carts(quantity,customer, product);
        cart_service.addToCart(cart);
        return "redirect:/web/detail_product/";
    }
}
