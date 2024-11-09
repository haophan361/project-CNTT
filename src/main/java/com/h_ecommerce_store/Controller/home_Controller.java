package com.h_ecommerce_store.Controller;

import com.h_ecommerce_store.DTO.request.postComment;
import com.h_ecommerce_store.DTO.response.product_Rating;
import com.h_ecommerce_store.Entity.Products;
import com.h_ecommerce_store.Entity.Shopping_Carts;
import com.h_ecommerce_store.Service.*;
import com.h_ecommerce_store.Util.Load_dataNavbar;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.h_ecommerce_store.DTO.response.detail_Product;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
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
    @GetMapping("/")
    public String home(Model model, @RequestParam(value="page", required=false, defaultValue="1")int page,
                       @RequestParam(value="listBrand",required = false) List<String> listBrand,
                       HttpServletRequest request)
    {
        load_dataNavbar.load_Navbar(model);
        load_dataNavbar.get_Type(model,listBrand);
        load_dataNavbar.get_Brand(model,"");
        load_dataNavbar.topSeller(model);
        Page<Products> products;
        if(listBrand==null)
        {
            products=product_service.getAllProducts(page,20);
        }
        else
        {
            products=product_service.getListProductByBrands(listBrand,page,20);
        }
        List<detail_Product> productsForPage = getContentProduct(products.getContent());
        model.addAttribute("url","home");
        model.addAttribute("listProducts", productsForPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("selectedBrands", listBrand);
        String brandParam = (listBrand != null) ? "?listBrand=" + String.join("%2C", listBrand) : "";
        model.addAttribute("requestURI", request.getRequestURI()+brandParam);
        return "web/home";
    }
    @GetMapping("/web/selectByName_Product")
    public String searchProduct_ByName(Model model, @RequestParam(value = "keyword",required = false) String keyword,
                                       @RequestParam(value="page", required=false, defaultValue="1") int page,
                                       @RequestParam(value="listBrand",required = false) List<String> listBrand,HttpServletRequest request)
    {
        load_dataNavbar.load_Navbar(model);
        load_dataNavbar.get_Type(model,listBrand);
        load_dataNavbar.get_Brand(model,"");
        load_dataNavbar.topSeller(model);
        Page<Products> products;
        if(listBrand==null)
        {
            products=product_service.noBrand_selectByName_Products(keyword,page,20);
        }
        else
        {
            products=product_service.selectByName_Products(keyword,listBrand,page,20);
        }
        List<detail_Product> productsForPage = getContentProduct(products.getContent());
        model.addAttribute("listProducts", productsForPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("selectedBrands", listBrand);
        String brandParam = (listBrand != null) ? "&listBrand=" + String.join("%2C", listBrand) : "";
        model.addAttribute("requestURI", request.getRequestURI()+"?keyword="+keyword+brandParam);
        return "web/home";
    }
    @GetMapping("/web/selectProductDiscount")
    public String selectProductDiscount(Model model, @RequestParam(value="page", required=false, defaultValue="1")int page,
                       @RequestParam(value="listBrand",required = false) List<String> listBrand,
                       HttpServletRequest request)
    {
        load_dataNavbar.load_Navbar(model);
        load_dataNavbar.get_Type(model,listBrand);
        load_dataNavbar.get_Brand(model,"");
        load_dataNavbar.topSeller(model);
        Page<Products> products;
        if(listBrand==null)
        {
            products=product_service.noBrand_getLisProductDiscount(page,20);
        }
        else
        {
            products=product_service.getListProduct_Discount(listBrand,page,20);
        }
        List<detail_Product> productsForPage = getContentProduct(products.getContent());
        model.addAttribute("url","discount");
        model.addAttribute("listProducts", productsForPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("selectedBrands", listBrand);
        String brandParam = (listBrand != null) ? "?listBrand=" + String.join("%2C", listBrand) : "";
        model.addAttribute("requestURI", request.getRequestURI()+brandParam);
        return "web/home";
    }
    @GetMapping("/web/selectProductByType")
    public String selectProductByType(Model model,@RequestParam(value = "productType",required = false) String productType, @RequestParam(value="page", required=false, defaultValue="1")int page,
                                      @RequestParam(value="listBrand",required = false) List<String> listBrand,
                                      HttpServletRequest request)
    {
        load_dataNavbar.load_Navbar(model);
        load_dataNavbar.get_Type(model,listBrand);
        load_dataNavbar.get_Brand(model,productType);
        load_dataNavbar.topSeller(model);
        Page<Products> products;
        if(listBrand==null)
        {
            products=product_service.noBrand_getListProductByType(productType,page,20);
        }
        else
        {
            products=product_service.getListProductByType(productType,listBrand,page,20);
        }
        List<detail_Product> productsForPage = getContentProduct(products.getContent());
        model.addAttribute("url",productType);
        model.addAttribute("listProducts", productsForPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("selectedBrands", listBrand);
        String brandParam = (listBrand != null) ? "&listBrand=" + String.join("%2C", listBrand) : "";
        model.addAttribute("requestURI", request.getRequestURI()+"?productType="+productType+brandParam);
        return "web/home";
    }
    public List<detail_Product> getContentProduct(List<Products> productPage)
    {
        List<detail_Product> detailProducts = new ArrayList<>();
        for (Products product : productPage)
        {
            product_Rating product_rating = comment_service.getRatingProduct(product.getID());
            detail_Product detailProduct = new detail_Product(
                    product.getID(),
                    product.getProduct_name(),
                    product.getImage_url(),
                    product.getCost(),
                    product.getDiscount(),
                    product.getQuantity(),
                    product.getProduct_type(),
                    product_rating.getRate(),
                    product_rating.getCounting()
            );
            detailProducts.add(detailProduct);
        }
        return detailProducts;
    }
    @GetMapping("/web/detail_product/{ID}")
    public String getDetail_Product(Model model, @PathVariable("ID") int ID)
    {
        load_dataNavbar.load_Navbar(model);
        String username = account_service.getLoggedUserName();
        if (!username.equals("anonymousUser"))
        {
            Shopping_Carts cart = cart_service.getCartByproduct_account(ID, username);
            if (cart != null)
            {
                model.addAttribute("selected", cart.getQuantity());
            }
        }
        Products product = product_service.getProductsByID(ID);
        List<Products> relatedProducts = product_service.getRelatedProductByType(product.getProduct_type(), ID);

        relatedProducts.sort((p1, p2) -> Integer.compare(Math.abs(p1.getID() - ID), Math.abs(p2.getID() - ID)));
        if (relatedProducts.size() > 4)
        {
            relatedProducts = relatedProducts.subList(0, 4);
        }
        List<detail_Product> products = getContentProduct(relatedProducts);
        product_Rating product_rating = comment_service.getRatingProduct(ID);
        double rating = product_rating.getRate();
        Long counting = product_rating.getCounting();
        detail_Product detail_product = new detail_Product(
                ID,
                product.getProduct_name(),
                product.getImage_url(),
                product.getCost(),
                product.getDiscount(),
                product.getQuantity(),
                product.getProduct_type(),
                rating,
                counting
        );
        model.addAttribute("relatedProducts", products);
        model.addAttribute("detail_Product", detail_product);
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