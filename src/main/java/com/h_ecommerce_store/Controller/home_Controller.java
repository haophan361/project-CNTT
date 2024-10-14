package com.h_ecommerce_store.Controller;

import com.h_ecommerce_store.DTO.request.postComment;
import com.h_ecommerce_store.DTO.response.list_ShoppingCart;
import com.h_ecommerce_store.DTO.response.product_Rating;
import com.h_ecommerce_store.Model.Accounts;
import com.h_ecommerce_store.Model.Products;
import com.h_ecommerce_store.Model.Shopping_Carts;
import com.h_ecommerce_store.Service.account_Service;
import com.h_ecommerce_store.Service.cart_Service;
import com.h_ecommerce_store.Service.comment_Service;
import com.h_ecommerce_store.Service.product_Service;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.h_ecommerce_store.DTO.response.detail_Product;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import com.h_ecommerce_store.DTO.response.comment_Product;
import org.apache.pdfbox.pdmodel.PDDocument;


@Controller
public class home_Controller
{
    @Autowired
    private product_Service product_service;
    @Autowired
    private comment_Service comment_service;
    @Autowired
    private account_Service account_service;
    @Autowired
    private cart_Service cart_service;
    @GetMapping({"/"})
    public String home(Model model)
    {
        model.addAttribute("listProducts", product_service.getAllProducts());
        String username=account_service.getLoggedUserName();
        if(username.equals("anonymousUser"))
        {
            return "web/home";
        }
        Accounts account=account_service.getAccount(username);
        List<Shopping_Carts> list_cart= cart_service.getCart_Customer(username);
        List<list_ShoppingCart> list_Cart=new ArrayList<>();
        BigDecimal total=new BigDecimal(0);
        for(Shopping_Carts cart :list_cart)
        {
            list_ShoppingCart Cart=new list_ShoppingCart(cart.getProduct().getProduct_name(),
                    cart.getQuantity(),cart.getProduct().getCost(),cart.getProduct().getDiscount(),cart.getProduct().getImage_url());
            total=total.add(Cart.getNew_price());
            list_Cart.add(Cart);
        }
        model.addAttribute("number_type", list_Cart.size());
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formatted_total=decimalFormat.format(total);
        model.addAttribute("total",formatted_total);
        model.addAttribute("list_cart",list_Cart);
        if(account.getRole().equals("ROLE_USER"))
        {
            return "web/home";
        }
        else
        {
            return "admin/home";
        }
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
        BigDecimal discount_price = (new BigDecimal(discount).multiply(price))
                .divide(new BigDecimal(100), 0, RoundingMode.FLOOR);
        BigDecimal new_price=price.subtract(discount_price);
        String product_type=product.getProduct_type();
        product_Rating product_rating = comment_service.getRatingProduct(ID);
        double rating=product_rating.getRate();
        Long counting=product_rating.getCounting();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedNewPrice = decimalFormat.format(new_price);
        String formattedOldPrice = decimalFormat.format(price);
        detail_Product detail_product = new detail_Product(ID,name,image_url,price,quantity,new_price,product_type,rating,counting);
        model.addAttribute("formatted_newPrice", formattedNewPrice + "đ");
        model.addAttribute("formatted_oldPrice", formattedOldPrice + "đ");
        model.addAttribute("detail_Product",detail_product);
        return "web/detail_product";
    }
    @GetMapping("/web/description_product/{ID}")
    public String getDescription_product(Model model, @PathVariable("ID") int ID)
    {
        Products product = product_service.getProductsByID(ID);
        String description_file = product.getDetail();
        StringBuilder htmlContent = new StringBuilder("<html><body>");
        try
        {
            URI fileUri = new URI(description_file);
            URL fileUrl = fileUri.toURL();
            try (InputStream inputStream = fileUrl.openStream();
                 PDDocument document = PDDocument.load(inputStream))
            {
                PDFTextStripper pdfStripper = new PDFTextStripper();
                String text = pdfStripper.getText(document);
                htmlContent.append("<p>").append(text.replace("\n", "<br>")).append("</p>");
            }
        }
        catch (Exception e)
        {
            model.addAttribute("description", "Không thể load dữ liệu");
            return "web/detail_product :: tab1";
        }
        htmlContent.append("</body></html>");
        model.addAttribute("description", htmlContent.toString());
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
        if (num!=0)
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