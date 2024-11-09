package com.h_ecommerce_store.Controller;

import com.h_ecommerce_store.DTO.request.updateProfile;
import com.h_ecommerce_store.Entity.Customers;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import com.h_ecommerce_store.Service.account_Service;
import com.h_ecommerce_store.Service.customer_Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@RequiredArgsConstructor
@Controller
public class customer_Controller
{
    private final account_Service account_service;
    private final customer_Service customer_service;
    @GetMapping("/user/formProfile")
    public String formProfile(Model model)
    {
        String username=account_service.getLoggedUserName();
        Customers customer=customer_service.getCustomer(username);
        String name=customer.getCus_name();
        String phone=customer.getPhone();
        String[] address=customer.getAddress().split(", ");
        String city=address[0];
        model.addAttribute("cityName",city);
        String district=address[1];
        model.addAttribute("districtName",district);
        String ward=address[2];
        model.addAttribute("wardName",ward);
        String houseNo=address[3];
        model.addAttribute("houseNo",houseNo);
        updateProfile update_profile= new updateProfile(name,username,phone);
        model.addAttribute("updateProfile", update_profile);
        return "web/updateProfile";
    }
    @PostMapping("/user/updateProfile")
    public String updateProfile(@Valid @ModelAttribute("updateProfile") updateProfile updateProfile
            , BindingResult result,
                                @RequestParam(value = "cityName",required = true) String city,
                                @RequestParam(value = "districtName",required = true) String district,
                                @RequestParam(value = "wardName",required = true) String ward,
                                @RequestParam(value = "houseNo",required = true) String houseNo)
    {
        if(result.hasErrors())
        {
            return "web/updateProfile";
        }
        String name=updateProfile.getName();
        String address=city+", "+district+", "+ward+", "+houseNo;
        String phone=updateProfile.getPhone();
        String username=updateProfile.getUsername();
        customer_service.updateProfile(name,address,phone,username);
        return "redirect:/user/formProfile";
    }
}
