package com.h_ecommerce_store.Controller;

import com.h_ecommerce_store.DTO.request.postComment;
import com.h_ecommerce_store.Entity.Comments;
import com.h_ecommerce_store.Entity.Customers;
import com.h_ecommerce_store.Entity.Products;
import com.h_ecommerce_store.Service.account_Service;
import com.h_ecommerce_store.Service.comment_Service;
import com.h_ecommerce_store.Service.customer_Service;
import com.h_ecommerce_store.Service.product_Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
@RequiredArgsConstructor
@Controller
public class comment_Controller
{
    private final comment_Service comment_service;
    private final account_Service account_service;
    private final customer_Service customer_service;
    private final product_Service product_service;
    @PostMapping("/user/postComment")
    public String post_Comment(@Valid @ModelAttribute("postComment") postComment postComment, BindingResult result)
    {
        int productID=postComment.getProductID();
        if(result.hasErrors())
        {
            return "/web/detail_product/"+productID+"#tab2";
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
