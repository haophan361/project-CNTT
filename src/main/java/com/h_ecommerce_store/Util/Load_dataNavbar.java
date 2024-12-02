package com.h_ecommerce_store.Util;

import com.h_ecommerce_store.DTO.response.*;
import com.h_ecommerce_store.Entity.Accounts;
import com.h_ecommerce_store.Entity.Products;
import com.h_ecommerce_store.Entity.Shopping_Carts;
import com.h_ecommerce_store.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Load_dataNavbar
{
    private final account_Service account_service;
    private final cart_Service cart_service;
    private final product_Service product_service;
    private final billDetail_Service billDetail_service;
    private final bill_Service bill_service;
    public void load_Navbar(Model model)
    {
        String username=account_service.getLoggedUserName();
        List<list_ShoppingCart> list_Cart=new ArrayList<>();
        if(!username.equals("anonymousUser"))
        {
            Accounts account=account_service.getAccount(username);
            List <Shopping_Carts> carts =cart_service.getCart_Customer(account.getUsername());
            list_Cart=cart_service.getCart_Customer(carts);
            model.addAttribute("number_type",cart_service.getCartCountByCus(username));
            model.addAttribute("number_bill",bill_service.CountBill_NotConfirmOrNotReceive(username));
            model.addAttribute("role",account.getRole());
        }
        List<String> typeProduct=product_service.getProductType();
        model.addAttribute("typeProduct",typeProduct);
        model.addAttribute("list_cart",list_Cart);
    }
    public void get_Type(Model model,List<String> brands,String product_name)
    {
        List<String> allType=product_service.getProductType();
        List<productType_asideWidget> productTypes=new ArrayList<>();
        for(String type : allType)
        {
            long count=product_service.countProduct_ByProductType(type,brands,product_name);
            productType_asideWidget productType=new productType_asideWidget(type,count);
            productTypes.add(productType);
        }
        model.addAttribute("allType",productTypes);
    }
    public void get_Brand(Model model,List<String> productType,String product_name)
    {
        List<String> allBrand=product_service.getProductBrand();
        List<brand_asideWidget> brands=new ArrayList<>();
        for(String brand :allBrand)
        {
            long count=product_service.countProductBrand_ByType(brand,productType,product_name);
            brand_asideWidget productBrand=new brand_asideWidget(brand,count);
            brands.add(productBrand);
        }
        model.addAttribute("allBrand",brands);
    }
    public void topSeller(Model model)
    {
        List<Products> products=billDetail_service.TopProductsSeller();
        List<topSeller_asideWidget> topSellerProduct=new ArrayList<>();
        for(Products product : products)
        {
            topSeller_asideWidget product_widget=new topSeller_asideWidget(product.getID(),
                    product.getProduct_name(),product.getImage_url(),product.getProduct_type(),
                    product.getCost(),product.getDiscount());
            topSellerProduct.add(product_widget);
        }
        model.addAttribute("topSellerProduct",topSellerProduct);
    }
}
