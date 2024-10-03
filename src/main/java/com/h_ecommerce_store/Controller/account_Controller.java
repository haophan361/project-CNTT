package com.h_ecommerce_store.Controller;
import com.h_ecommerce_store.DTO.request.changePassword;
import com.h_ecommerce_store.Model.Customers;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
        List<String> listEmail=account_service.findAllEmail();
        if(register.isEmailPresent(listEmail))
        {
            result.rejectValue("email","error.register",
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
        String email=register.getEmail();
        String password=register.getPassword();
        Accounts account=new Accounts(email,password);
        Customers customer=new Customers(name,email,address,phone);
        account_service.insertAccount(account);
        customer_service.insertCustomer(customer);
        return "redirect:/web/loginForm";
    }
    @GetMapping("/web/loginForm")
    public String loginForm(Model model)
    {
        model.addAttribute("login",new Login());
        return "web/login";
    }
    @PostMapping("/web/login")
    public String Login (@Valid @ModelAttribute("login") Login login, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "/web/login";
        }
        List<String> listEmail=account_service.findAllEmail();
        String email = login.getEmail();
        if(!listEmail.contains(email))
        {
            result.rejectValue("email","error.login",
                    "Tài khoản email không tồn tại");
            return "/web/login";
        }
        String password = login.getPassword();
        Accounts account = new Accounts(email, password);
        String role = account_service.checkLogin(account);

        if(role != null)
        {
            List<SimpleGrantedAuthority> authorities;

            if(role.equals("admin"))
            {
                authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(email, password, authorities));
                return "redirect:/admin/home";
            }
            else
            {
                authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(email, password, authorities));
                return "redirect:/";
            }
        }
        else
        {
            result.rejectValue("password", "error.login",
                    "Mật khẩu không chính xác");
            return "/web/login";
        }
    }
    @GetMapping("/user/form_changePassword")
    public String form_changePassword(Model model)
    {
        model.addAttribute("changePassword",new changePassword());
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
        Accounts account=account_service.getAccount(changePassword.getEmail());
        if(!changePassword.checkPassword(account.getPassword()))
        {
            result.rejectValue("oldPassword","error.changePassword",
                    "Mật khẩu hiện tại không khớp");
            return "/web/passwordForm";
        }
        account_service.changePassword(changePassword.getEmail(),changePassword.getNewPassword());
        return "redirect:/web/home";
    }
    @GetMapping("/user/profile")
    public String profile (Model model)
    {
        model.addAttribute("register", new Register());
        return "web/register";
    }
    @GetMapping("/logout")
    public String logout()
    {
        return "web/home";
    }
}
