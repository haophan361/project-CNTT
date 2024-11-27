package com.h_ecommerce_store.Controller;

import com.h_ecommerce_store.DTO.request.updateProfile;
import com.h_ecommerce_store.DTO.response.manage_User;
import com.h_ecommerce_store.Entity.Users;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.h_ecommerce_store.Service.account_Service;
import com.h_ecommerce_store.Service.user_Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class user_Controller
{
    private final account_Service account_service;
    private final user_Service user_service;
    @GetMapping("/user/formProfile")
    public String formProfile(Model model)
    {
        String username=account_service.getLoggedUserName();
        Users customer= user_service.getCustomer(username);
        String name=customer.getName();
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
                                @RequestParam(value = "cityName") String city,
                                @RequestParam(value = "districtName") String district,
                                @RequestParam(value = "wardName") String ward,
                                @RequestParam(value = "houseNo") String houseNo)
    {
        if(result.hasErrors())
        {
            return "web/updateProfile";
        }
        String name=updateProfile.getName();
        String address=city+", "+district+", "+ward+", "+houseNo;
        String phone=updateProfile.getPhone();
        String username=updateProfile.getUsername();
        user_service.updateProfile(name,address,phone,username);
        return "redirect:/user/formProfile";
    }
    @GetMapping("/admin/user")
    public String getUser(Model model,@RequestParam(value = "name",defaultValue = "") String name,
                          @RequestParam(value = "username",defaultValue = "") String username,
                          @RequestParam(value="phone",required = false,defaultValue = "") String phone,
                          @RequestParam(value="page", required=false, defaultValue="1") int page,
                          @RequestParam(value="role",required = false) List<String> role,
                          HttpServletRequest request)
    {
        Page<Users> users =user_service.getUser(name,username,role,phone,page,10);
        List<Users> listUser=users.getContent();
        List<manage_User> list_User = new ArrayList<>();
        for (Users user : listUser)
        {
            String user_role= account_service.getRoleByUsername(user.getEmail());
            manage_User manage_user = new manage_User(user.getEmail(),user.getName(),user.getAddress(),user.getPhone(),user_role);
            list_User.add(manage_user);
        }
        model.addAttribute("list_User",list_User);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("name",name);
        model.addAttribute("username",username);
        model.addAttribute("phone",phone);
        model.addAttribute("role",role);
        model.addAttribute("user_role",user_service.countUserByRole(name,username,"ROLE_USER",phone));
        model.addAttribute("staff_role",user_service.countUserByRole(name,username,"ROLE_STAFF",phone));
        String roleParam="";
        if(role!=null && !role.isEmpty())
        {
            roleParam="&role=" + String.join("%2C", role);
        }
        model.addAttribute("requestURI", request.getRequestURI()+"?username="+username+"&name="+name+"&phone="+phone+roleParam);
        return "admin/user";
    }
    @PostMapping("/admin/changeRole")
    @ResponseBody
    public ResponseEntity<String> changeRole(@RequestParam(value="role") String role,
                             @RequestParam(value = "username") String username)
    {
        account_service.changeRole(username, role);
        return ResponseEntity.ok("Role changed successfully!");
    }
}
