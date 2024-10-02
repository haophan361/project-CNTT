package com.h_ecommerce_store.Controller;
import com.h_ecommerce_store.DTO.request.changePassword;
import com.h_ecommerce_store.Model.Customers;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.h_ecommerce_store.Model.Accounts;
import com.h_ecommerce_store.DTO.request.Login;
import com.h_ecommerce_store.DTO.request.Register;
import com.h_ecommerce_store.Service.account_Service;
import com.h_ecommerce_store.Service.customer_Service;

import java.util.List;

@Controller
public class account_Controller
{
    @Autowired
    account_Service account_service;
    @Autowired
    customer_Service customer_service;
    @GetMapping("/registerForm")
    public String registerForm(Model model)
    {
        model.addAttribute("register", new Register());
        return "web/register";
    }
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("register") Register register, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "web/register";
        }
        if(!register.IsPasswordMatching())
        {
            result.rejectValue("confirmPassword", "error.register",
                    "Mật khẩu không trùng khớp");
            return "web/register";
        }
        List<String> listEmail=account_service.findAllEmail();
        if(register.isEmailPresent(listEmail))
        {
            result.rejectValue("email","error.register",
                    "Email đã tồn tại");
            return "web/register";
        }
        String name=register.getName();
        String city=register.getCity();
        String district=register.getDistrict();
        String ward=register.getWard();
        String houseNo=register.getHouseNo();
        String address=city+", "+district+", "+ward+", "+houseNo;
        String phone=register.getPhone();
        String email=register.getEmail();
        String password=register.getPassword();
        Accounts account=new Accounts(email,password);
        Customers customer=new Customers(name,email,address,phone);
        account_service.insertAccount(account);
        customer_service.insertCustomer(customer);
        return "redirect:/loginForm";
    }
    @GetMapping("/loginForm")
    public String loginForm(Model model)
    {
        model.addAttribute("login",new Login());
        return "web/login";
    }
    @PostMapping("/login")
    public String Login (@ModelAttribute("account") Login login)
    {
        String email=login.getEmail();
        String password=login.getPassword();
        Accounts account=new Accounts(email,password);
        String role= account_service.checkLogin(account);
        if(role!=null)
        {
            if(role.equals("admin"))
            {
                return "admin/home";
            }
            else
            {
                return "web/home";
            }
        }
        else
        {
            return "redirect:/loginForm";
        }
    }
    @GetMapping("/form_changePassword")
    public String form_changePassword(Model model)
    {
        model.addAttribute("changePassword",new changePassword());
        return "web/changePassword";
    }
    @PostMapping("changePassword")
    public String changePassword(@Valid @ModelAttribute("changePassword") changePassword changePassword,
                                 BindingResult result)
    {
        if(result.hasErrors())
        {
            return "web/changePassword";
        }
        if(!changePassword.isPasswordMatching())
        {
            result.rejectValue("confirmPassword", "error.changePassword",
                    "Mật khẩu không trùng khớp");
            return "web/changePassword";
        }
        Accounts account=account_service.getAccount(changePassword.getEmail());
        if(!changePassword.checkPassword(account.getPassword()))
        {
            result.rejectValue("oldPassword","error.changePassword",
                    "Mật khẩu hiện tại không khớp");
            return "web/changePassword";
        }
        account_service.changePassword(changePassword.getEmail(),changePassword.getNewPassword());
        return "redirect:/home";
    }
    @GetMapping("/logout")
    public String logout()
    {
        return "web/home";
    }
}
