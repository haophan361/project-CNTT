package com.h_ecommerce_store.Controller;

import com.h_ecommerce_store.DTO.request.postComment;
import com.h_ecommerce_store.DTO.response.product_Rating;
import com.h_ecommerce_store.Model.Products;
import com.h_ecommerce_store.Service.comment_Service;
import com.h_ecommerce_store.Service.product_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.h_ecommerce_store.DTO.response.detail_Product;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import com.h_ecommerce_store.DTO.response.comment_Product;
@Controller
public class home_Controller
{
    @Autowired
    private product_Service product_service;
    @Autowired
    private comment_Service comment_service;
    @GetMapping({"/"})
    public String home(Model model) throws Exception
    {
        model.addAttribute("listProducts", product_service.getAllProducts());
        return "web/home";
    }
    @GetMapping("/admin/home")
    public String admin_home(Model model)
    {
        model.addAttribute("listProducts", product_service.getAllProducts());
        return "admin/home";
    }
    @GetMapping("/web/detail_product/{ID}")
    public String getDetail_Product(Model model,@PathVariable("ID") int ID)
    {
        Products product = product_service.getProductsByID(ID);
        String name=product.getProduct_name();
        String image_url=product.getImage_url();
        BigDecimal price=product.getCost();
        int quantity=product.getQuantity();
        int discount=product.getDiscount();
        BigDecimal discountPercentage=new BigDecimal(discount).divide(new BigDecimal(100), RoundingMode.UNNECESSARY);
        BigDecimal new_price=price.subtract(price.multiply(discountPercentage));
        String description=product.getDetail();
        product_Rating product_rating = comment_service.getRatingProduct(ID);
        double rating=product_rating.getRate();
        Long counting=product_rating.getCounting();
        detail_Product detail_product = new detail_Product(ID,name,image_url,price,quantity,new_price,description,rating,counting);
        model.addAttribute("detail_Product",detail_product);
        return "web/detail_product";
    }
    @GetMapping("/web/description_product/{ID}")
    public String getDescription_product(Model model,@PathVariable("ID") int ID)
    {
        String description="Hehe";
        model.addAttribute("description",description);
        return "web/detail_product :: tab1";
    }
    @GetMapping("/web/rating_product/{ID}")
    public String getRating_product(Model model,@PathVariable("ID") int ID)
    {
        List<comment_Product> comments=comment_service.getCommentByProduct(ID);
        List<Long> ratings=new ArrayList<>();
        long avgRating=0L;
        int num=0;
        for(int i=1;i<=5;i++)
        {
            Long rating=comment_service.getNumberOfCommentsByRating(i,ID);
            if(rating==null)
            {
                rating=0L;
            }
            avgRating+=(rating*i);
            num+=rating;
            ratings.add(rating);
        }
        if (!ratings.isEmpty())
        {
            avgRating = avgRating / num;
        }
        model.addAttribute("avg_rating",avgRating);
        model.addAttribute("ratings",ratings);
        model.addAttribute("comments",comments);
        model.addAttribute("num",num);
        model.addAttribute("postComment",new postComment(ID));
        return "web/detail_product :: tab2";
    }
}