package com.h_ecommerce_store.Controller;

import com.h_ecommerce_store.DTO.request.postComment;
import com.h_ecommerce_store.DTO.response.product_Rating;
import com.h_ecommerce_store.Model.Accounts;
import com.h_ecommerce_store.Model.Products;
import com.h_ecommerce_store.Model.Shopping_Carts;
import com.h_ecommerce_store.Service.*;
import com.h_ecommerce_store.Util.Load_dataNavbar;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.text.PDFTextStripper;
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
@RequiredArgsConstructor
public class home_Controller
{
    private final product_Service product_service;
    private final comment_Service comment_service;
    private final account_Service account_service;
    private final cart_Service cart_service;
    private final Load_dataNavbar load_dataNavbar;
    private final bill_Service bill_service;
    @GetMapping({"/"})
    public String home(Model model)
    {
        load_dataNavbar.load_navbarHome(model);
        String username=account_service.getLoggedUserName();
        model.addAttribute("listProducts", product_service.getAllProducts());
        if(username.equals("anonymousUser"))
        {
            return "web/home";
        }
        Accounts account=account_service.getAccount(username);
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
        load_dataNavbar.load_navbarHome(model);
        String username=account_service.getLoggedUserName();
        if(!username.equals("anonymousUser"))
        {
            Shopping_Carts cart=cart_service.getCartByproduct_account(ID,username);
            if(cart!=null)
            {
                model.addAttribute("selected",cart.getQuantity());
            }
        }
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
        String username=account_service.getLoggedUserName();
        long allowCommentTime=0L;
        if(!username.equals("anonymousUser"))
        {
            Long numberTimeBuyPro= bill_service.getNumberTimeBuyPro(username,ID);
            Long numberTimeComment= comment_service.getNumberOfComments(username,ID);
            allowCommentTime=numberTimeBuyPro-numberTimeComment;
        }
        model.addAttribute("avg_rating",avgRating);
        model.addAttribute("ratings",ratings);
        model.addAttribute("comments",comments);
        model.addAttribute("num",num);
        model.addAttribute("postComment",new postComment(ID,allowCommentTime));
        return "web/detail_product :: tab2";
    }
}