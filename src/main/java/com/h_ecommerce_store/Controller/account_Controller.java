package com.h_ecommerce_store.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.h_ecommerce_store.Model.Accounts;
import com.h_ecommerce_store.DTO.request.Login;
import com.h_ecommerce_store.DTO.request.Register;
import com.h_ecommerce_store.Service.account_Service;
@Controller
public class account_Controller
{
    @Autowired
    account_Service account_service;
    @GetMapping("/registerForm")
    public String registerForm(Model model)
    {
        model.addAttribute("register", new Register());
        return "web/register";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute("register") Register register)
    {
        return "web/register";
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
        if(account_service.checkLogin(account))
        {
            String role=account.getRole();
            if(role.equals("Admin"))
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
    @GetMapping("/logout")
    public String logout()
    {
        return "web/home";
    }
}
