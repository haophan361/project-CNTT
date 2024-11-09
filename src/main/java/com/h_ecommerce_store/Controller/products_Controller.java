package com.h_ecommerce_store.Controller;

import com.h_ecommerce_store.Entity.Bills;
import com.h_ecommerce_store.Entity.Products;
import com.h_ecommerce_store.Service.billDetail_Service;
import com.h_ecommerce_store.Service.bill_Service;
import com.h_ecommerce_store.Service.file_Service;
import com.h_ecommerce_store.Service.product_Service;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Controller
@RequestMapping
public class products_Controller
{
    private final product_Service product_service;
    private final file_Service file_Service;
    private final billDetail_Service billDetail_service;
    private final bill_Service bill_service;
    @PostMapping("/admin/saveProducts")
    public String saveProduct(@ModelAttribute("product") Products product, @RequestParam("imageInput") MultipartFile imageFile,@RequestParam("description") MultipartFile descriptionFile)
    {
        if (!imageFile.isEmpty())
        {
            String image = file_Service.upload(imageFile,"media");
            product.setImage_url(image);
        }
        if(!descriptionFile.isEmpty())
        {
            String description = file_Service.upload(descriptionFile,"application/pdf");
            product.setDetail(description);
        }
        product_service.saveProduct(product);
        return "redirect:/admin/crudProducts";
    }
    @PostMapping("/admin/updateProduct")
    public String updateProduct(@ModelAttribute("product") Products product, @RequestParam("imageInput") MultipartFile imageFile,@RequestParam("description")MultipartFile descriptionFile)
    {
        if (!imageFile.isEmpty())
        {
            String image = file_Service.upload(imageFile,"media");
            product.setImage_url(image);
        }
        if(!descriptionFile.isEmpty())
        {
            String description = file_Service.upload(descriptionFile,"application/pdf");
            product.setDetail(description);
        }
        product_service.updateProduct(product);

        return "redirect:/admin/crudProducts";
    }

    @GetMapping("/admin/editProduct/{id}")
    public String showFromForUpdate(@PathVariable(value = "id") int id, Model model)
    {
        Products product = product_service.getProductsByID(id);
        model.addAttribute("product",product);
        return "admin/updateProduct";
    }

    @PostMapping("/admin/deleteProduct")
    public String deleteProduct(@RequestParam(value = "productID") int ID, Model model)
    {
        List<Integer> billIDs = billDetail_service.getBillIDByProductID(ID);
        List<Bills> bills= new ArrayList<>();
        for(Integer billID : billIDs)
        {
            Bills bill=bill_service.getBillByID(billID);
            bills.add(bill);
        }
        List<String> constraint=new ArrayList<>();
        for (Bills bill : bills)
        {
            LocalDateTime receiveDate = bill.getReceive_date();
            if (receiveDate != null)
            {
                LocalDateTime now=LocalDateTime.now();
                long days=ChronoUnit.DAYS.between(receiveDate,now);
                if(days<30)
                {
                    constraint.add("Hóa đơn "+bill.getID()+" phải được hoàn thành thủ tục hơn 30 ngày mới có thể xóa");
                }
            }
        }
        if(constraint.isEmpty())
        {
            product_service.deleteProductsById(ID);
            return "redirect:/admin/crudProducts";
        }
        else
        {
            model.addAttribute("error_message",constraint);
            return "/web/error";
        }
    }
    @GetMapping("/admin/crudProducts")
    public String crudProducts(Model model,@RequestParam(value="page", required=false, defaultValue="1")int page,
                               HttpServletRequest request)
    {
        Page<Products> products=product_service.getAllProducts(page,20);
        List<Products> productsForPage = products.getContent();
        model.addAttribute("listProducts", productsForPage);
        model.addAttribute("listType",product_service.getProductType());
        model.addAttribute("countAll",product_service.totalProduct());
        model.addAttribute("count_inStock",product_service.countProduct_inStock());
        model.addAttribute("count_outOfStock",product_service.countProduct_outOfStock());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/crud_products";
    }
    @GetMapping("/admin/crudProducts/inStock")
    public String crudProducts_inStock(Model model,@RequestParam(value="page", required=false, defaultValue="1")int page,
                               HttpServletRequest request)
    {
        Page<Products> products=product_service.getLisProduct_inStock(page,20);
        List<Products> productsForPage = products.getContent();
        model.addAttribute("listProducts", productsForPage);
        model.addAttribute("listType",product_service.getProductType());
        model.addAttribute("countAll",product_service.totalProduct());
        model.addAttribute("count_inStock",product_service.countProduct_inStock());
        model.addAttribute("count_outOfStock",product_service.countProduct_outOfStock());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/crud_products";
    }
    @GetMapping("/admin/crudProducts/outOfStock")
    public String crudProducts_oufOfStock(Model model,@RequestParam(value="page", required=false, defaultValue="1")int page,
                                       HttpServletRequest request)
    {
        Page<Products> products=product_service.getLisProduct_outOfStock(page,20);
        List<Products> productsForPage = products.getContent();
        model.addAttribute("listProducts", productsForPage);
        model.addAttribute("listType",product_service.getProductType());
        model.addAttribute("countAll",product_service.totalProduct());
        model.addAttribute("count_inStock",product_service.countProduct_inStock());
        model.addAttribute("count_outOfStock",product_service.countProduct_outOfStock());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/crud_products";
    }
    @GetMapping("/admin/createProducts")
    public String createProduct(Model model)
    {
        Products product = new Products();
        model.addAttribute("product", product);
        return "admin/addProduct";
    }
    @GetMapping("/admin/searchByName")
    public String searchProductByName(Model model,@RequestParam(value = "keyword",required = false)String keyword,
                                @RequestParam(value="page", required=false, defaultValue="1")int page,
                                HttpServletRequest request)
    {
        Page<Products> products=product_service.noBrand_selectByName_Products(keyword,page,20);
        List<Products> productsForPage = products.getContent();
        model.addAttribute("listProducts", productsForPage);
        model.addAttribute("listType",product_service.getProductType());
        model.addAttribute("countAll",product_service.totalProduct());
        model.addAttribute("count_inStock",product_service.countProduct_inStock());
        model.addAttribute("count_outOfStock",product_service.countProduct_outOfStock());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("requestURI", request.getRequestURI()+"?keyword="+keyword);
        return "admin/crud_products";
    }
    @GetMapping("admin/searchByProductType")
    public String searchByProductType(Model model,@RequestParam(value = "productType")String productType,
                                      @RequestParam(value="page", required=false, defaultValue="1")int page,
                                      HttpServletRequest request)
    {
        Page<Products> products=product_service.noBrand_getListProductByType(productType,page,20);
        List<Products> productsForPage = products.getContent();
        model.addAttribute("listProducts", productsForPage);
        model.addAttribute("listType",product_service.getProductType());
        model.addAttribute("countAll",product_service.totalProduct());
        model.addAttribute("count_inStock",product_service.countProduct_inStock());
        model.addAttribute("count_outOfStock",product_service.countProduct_outOfStock());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("requestURI", request.getRequestURI()+"?productType="+productType);
        return "admin/crud_products";
    }
}
