package com.h_ecommerce_store.Controller;

import com.h_ecommerce_store.Service.product_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class home_Controller
{
    @Autowired
    private product_Service productService;
    @GetMapping({"/"})
    public String home(Model model) throws Exception
    {
        model.addAttribute("listProducts", productService.getAllProducts());
        return "web/home";
    }
    @GetMapping("/admin/home")
    public String admin_home(Model model)
    {
        model.addAttribute("listProducts", productService.getAllProducts());
        return "admin/home";
    }
}