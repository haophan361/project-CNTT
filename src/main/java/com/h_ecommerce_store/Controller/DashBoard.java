package com.h_ecommerce_store.Controller;

import com.h_ecommerce_store.DTO.response.topSeller_Product;
import com.h_ecommerce_store.DTO.response.revenue_Time;
import com.h_ecommerce_store.DTO.response.revenue_productType;
import com.h_ecommerce_store.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class DashBoard
{
    private final customer_Service customer_service;
    private final product_Service product_service;
    private final bill_Service bill_service;
    private final billDetail_Service billDetail_service;
    private final cart_Service cart_service;
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###");
    @GetMapping("/admin/dashboard")
    public String dashBoard(Model model)
    {
        Long totalProduct= product_service.totalProduct();
        model.addAttribute("totalProduct",totalProduct);

        Double totalRevenue=bill_service.totalRevenue();
        String formattedPrice=decimalFormat.format(totalRevenue);
        model.addAttribute("totalRevenue",formattedPrice);

        Long totalCart=cart_service.totalCart();
        model.addAttribute("totalCart",totalCart);

        Long totalCustomer=customer_service.totalCustomers();
        model.addAttribute("totalCustomer",totalCustomer);
        List<String> productTypes=product_service.getProductType();
        model.addAttribute("productType",productTypes);
        return "admin/DashBoard";
    }
    @GetMapping("/admin/revenue_typeProduct")
    @ResponseBody
    public List<revenue_productType> revenue_productType()
    {
        Double totalRevenue=bill_service.totalRevenue();
        List<String> productTypes=product_service.getProductType();
        List<revenue_productType> revenueProductTypeList = new ArrayList<>();
        for(String type : productTypes)
        {
            Double revenue_percent=(billDetail_service.totalRevenueByProductType(type)/totalRevenue)*100;
            revenueProductTypeList.add(new revenue_productType(type,revenue_percent));
        }
        return revenueProductTypeList;
    }
    @GetMapping("/admin/revenue_Month")
    @ResponseBody
    public List<revenue_Time> revenue_Times(@RequestParam("year") int year)
    {
        List<revenue_Time> revenueTimes = new ArrayList<>();
        for (int month = 1; month <= 12; month++)
        {
            Double revenue = bill_service.getRevenue_Time(year, month);
            revenueTimes.add(new revenue_Time(month,revenue));
        }
        return revenueTimes;
    }
    @GetMapping("/admin/revenue_Product")
    @ResponseBody
    public List<topSeller_Product> topSellerProducts(@RequestParam("numberProduct")int numberProduct)
    {
        List<String> product_names=product_service.getProductName();
        List<topSeller_Product> topSellerProductList=new ArrayList<>();
        for(String product_name : product_names)
        {
            Double revenue=billDetail_service.totalRevenueByProduct(product_name);
            topSellerProductList.add(new topSeller_Product(product_name,revenue));
        }
        topSellerProductList.sort(Comparator.comparing(topSeller_Product::getRevenue_product).reversed());
        if (numberProduct < topSellerProductList.size())
        {
            return topSellerProductList.subList(0, numberProduct);
        }
        else
        {
            return topSellerProductList;
        }
    }
}
