package com.h_ecommerce_store.Util;

import com.h_ecommerce_store.DTO.response.list_ShoppingCart;
import com.h_ecommerce_store.Model.Accounts;
import com.h_ecommerce_store.Service.account_Service;
import com.h_ecommerce_store.Service.cart_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Load_dataNavbar
{
    private final account_Service account_service;
    private final cart_Service cart_service;
    public void load_navbarHome(Model model)
    {
        String username=account_service.getLoggedUserName();

        BigDecimal total=new BigDecimal(0);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        List<list_ShoppingCart> list_Cart=new ArrayList<>();
        if(!username.equals("anonymousUser"))
        {
            Accounts account=account_service.getAccount(username);
            list_Cart=cart_service.getCart_Customer(account.getUsername());
            model.addAttribute("number_type", list_Cart.size());
            for(list_ShoppingCart cart:list_Cart)
            {
                total=total.add(cart.getNew_price());
            }
            model.addAttribute("role",account.getRole());
        }
        String formatted_total=decimalFormat.format(total);
        model.addAttribute("total",formatted_total);
        model.addAttribute("list_cart",list_Cart);
    }
}
