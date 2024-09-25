package com.h_ecommerce_store.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class customer_Controller
{
    @GetMapping("/")
    public String home()
    {
        return "web/store";
    }
}
