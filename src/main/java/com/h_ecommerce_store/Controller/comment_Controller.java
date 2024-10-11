package com.h_ecommerce_store.Controller;

import com.h_ecommerce_store.DTO.request.postComment;
import com.h_ecommerce_store.Model.Comments;
import com.h_ecommerce_store.Model.Customers;
import com.h_ecommerce_store.Model.Products;
import com.h_ecommerce_store.Service.account_Service;
import com.h_ecommerce_store.Service.comment_Service;
import com.h_ecommerce_store.Service.customer_Service;
import com.h_ecommerce_store.Service.product_Service;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class comment_Controller
{
    @Autowired
    comment_Service comment_service;
    @Autowired
    account_Service account_service;
    @Autowired
    customer_Service customer_service;
    @Autowired
    product_Service product_service;
    @PostMapping("/user/postComment")
    public String post_Comment(@Valid @ModelAttribute("postComment") postComment postComment, BindingResult result)
    {
        int productID=postComment.getProductID();
        if (result.hasErrors())
        {
            return "web/detail_product :: tab2";
        }
        String comment=postComment.getComment();
        int rate=postComment.getRate();
        String username=account_service.getLoggedUserName();
        Customers customer=customer_service.getCustomer(username);
        Products product=product_service.getProductsByID(productID);
        Comments comments=new Comments(comment,rate,product,customer);
        comment_service.postComment(comments);
        return "redirect:/web/detail_product/"+productID+"#tab2";
    }
}
