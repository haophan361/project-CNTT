package com.h_ecommerce_store.Controller;

import com.h_ecommerce_store.DTO.response.list_ShoppingCart;
import com.h_ecommerce_store.Model.Accounts;
import com.h_ecommerce_store.Model.Customers;
import com.h_ecommerce_store.Model.Products;
import com.h_ecommerce_store.Model.Shopping_Carts;
import com.h_ecommerce_store.Service.account_Service;
import com.h_ecommerce_store.Service.cart_Service;
import com.h_ecommerce_store.Service.customer_Service;
import com.h_ecommerce_store.Service.product_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
@RequiredArgsConstructor
@Controller
public class cart_Controller
{
    private final cart_Service cart_service;
    private final product_Service product_service;
    private final account_Service account_service;
    private final customer_Service customer_service;
    @PostMapping("/user/addToCart/{productID}/{quantity}")
    public ResponseEntity<String> addToCart(@PathVariable("productID") int productID, @PathVariable("quantity") int quantity)
    {
        Products product=product_service.getProductsByID(productID);
        String username=account_service.getLoggedUserName();
        Customers customer=customer_service.getCustomer(username);
        Shopping_Carts cart=new Shopping_Carts(quantity,customer, product);
        cart_service.addToCart(cart);
        return ResponseEntity.ok("/web/detail_product/"+productID);
    }
    @PostMapping("/user/updateCart/{productID}/{quantity}")
    public ResponseEntity<String> updateCart(@PathVariable("productID") int productID,@PathVariable("quantity") int quantity)
    {
        Products product=product_service.getProductsByID(productID);
        String username=account_service.getLoggedUserName();
        Customers customer=customer_service.getCustomer(username);
        int cartID= cart_service.getCartID(productID,username);
        Shopping_Carts cart=new Shopping_Carts(cartID,quantity,customer,product);
        cart_service.updateCart(cart);
        return ResponseEntity.ok("/user/shoppingCart");
    }
    @PostMapping("/user/deleteCart/{cartID}")
    public ResponseEntity<String> deleteCart(@PathVariable("cartID") int cartID)
    {
        cart_service.deleteCart(cartID);
        return ResponseEntity.ok("/user/shoppingCart");
    }
    @GetMapping("/user/shoppingCart")
    public String shoppingCart(Model model)
    {
        String username=account_service.getLoggedUserName();
        Accounts account=account_service.getAccount(username);
        List<list_ShoppingCart> list_Cart=cart_service.getCart_Customer(account.getUsername());
        model.addAttribute("number_type", list_Cart.size());
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        BigDecimal total=new BigDecimal(0);
        for(list_ShoppingCart cart:list_Cart)
        {
            total=total.add(cart.getNew_price());
        }
        String formatted_total=decimalFormat.format(total);
        model.addAttribute("total",formatted_total);
        model.addAttribute("list_cart",list_Cart);
        model.addAttribute("role",account.getRole());
        return "web/shoppingCart";
    }
}
