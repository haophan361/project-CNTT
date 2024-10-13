package com.h_ecommerce_store.Controller;
import com.h_ecommerce_store.DTO.request.changePassword;
import com.h_ecommerce_store.Model.Customers;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.h_ecommerce_store.Model.Accounts;
import com.h_ecommerce_store.DTO.request.Register;
import com.h_ecommerce_store.Service.account_Service;
import com.h_ecommerce_store.Service.customer_Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class account_Controller
{
    @Autowired
    account_Service account_service;
    @Autowired
    customer_Service customer_service;
    @Autowired
    PasswordEncoder passwordEncoder;
    @GetMapping("/web/registerForm")
    public String registerForm(Model model)
    {
        model.addAttribute("register", new Register());
        return "web/register";
    }
    @PostMapping("/web/register")
    public String register(@Valid @ModelAttribute("register") Register register, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "/web/register";
        }
        if(!register.IsPasswordMatching())
        {
            result.rejectValue("confirmPassword", "error.register",
                    "Mật khẩu không trùng khớp");
            return "/web/register";
        }
        List<String> listPhone=customer_service.listPhone();
        if(register.isPhonePresent(listPhone))
        {
            result.rejectValue("phone","error.register",
                    "Số điện thoại đã tồn tại");
            return "/web/register";
        }
        List<String> listEmail=account_service.findAllEmail();
        if(register.isEmailPresent(listEmail))
        {
            result.rejectValue("username","error.register",
                    "Email đã tồn tại");
            return "/web/register";
        }
        String name=register.getName();
        String city=register.getCity();
        String district=register.getDistrict();
        String ward=register.getWard();
        String houseNo=register.getHouseNo();
        String address=city+", "+district+", "+ward+", "+houseNo;
        String phone=register.getPhone();
        String username=register.getUsername();
        String password=register.getPassword();
        Accounts account=new Accounts(username,password);
        Customers customer=new Customers(name,username,address,phone);
        account_service.insertAccount(account);
        customer_service.insertCustomer(customer);
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model)
    {
        if (error != null)
        {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng.");
        }
        return "/web/login";
    }
    @GetMapping("/user/form_changePassword")
    public String form_changePassword(Model model)
    {
        String username=account_service.getLoggedUserName();
        model.addAttribute("changePassword",new changePassword(username));
        return "/web/passwordForm";
    }
    @PostMapping("/user/changePassword")
    public String changePassword(@Valid @ModelAttribute("changePassword") changePassword changePassword,
                                 BindingResult result)
    {
        if(result.hasErrors())
        {
            return "/web/passwordForm";
        }
        if(!changePassword.isPasswordMatching())
        {
            result.rejectValue("confirmPassword", "error.changePassword",
                    "Mật khẩu không trùng khớp");
            return "/web/passwordForm";
        }
        Accounts account=account_service.getAccount(changePassword.getUsername());
        if(!passwordEncoder.matches(changePassword.getOldPassword(), account.getPassword()))
        {
            result.rejectValue("oldPassword","error.changePassword",
                    "Mật khẩu hiện tại không khớp");
            return "/web/passwordForm";
        }
        account_service.changePassword(changePassword.getUsername(),changePassword.getNewPassword());
        return "redirect:/";
    }
}
