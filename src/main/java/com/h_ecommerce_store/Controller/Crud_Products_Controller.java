package com.h_ecommerce_store.Controller;

import com.h_ecommerce_store.Model.Products;
import com.h_ecommerce_store.Service.ProductService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping
public class Crud_Products_Controller {
    @Autowired
    private ProductService productService;

    @PostMapping("/saveProducts")
    public String saveProduct(@ModelAttribute("product") Products product, @RequestParam("imageInput") MultipartFile imageFile) {
        if (!imageFile.isEmpty()) {
            String fileName = imageFile.getOriginalFilename();
            product.setImage_url(fileName);
        }
        productService.saveProduct(product); // Lưu sản phẩm
        return "redirect:/crudProducts"; // Chuyển hướng đến trang chủ
    }
    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute("product") Products product, @RequestParam("imageInput") MultipartFile imageFile) {
        if (!imageFile.isEmpty()) {
            String fileName = imageFile.getOriginalFilename();
            product.setImage_url(fileName);
        }
        productService.updateProduct(product);

        return "redirect:/crudProducts"; // Chuyển hướng đến trang chủ
    }

    @GetMapping("/editProduct/{id}")
    public String showFromForUpdate(@PathVariable(value = "id") int id, Model model){
        Products product =    productService.getProductsByID(id);
        model.addAttribute("product",product);
            return "web/updateProduct";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable (value = "id") int id) {
        this.productService.deleteProductsById(id);
        return "redirect:/crudProducts";
    }
    @GetMapping("/crudProducts")
    public String crudProducts(Model model)
    {
        model.addAttribute("listProducts", productService.getAllProducts());
        return "web/crud_products";
    }
    @GetMapping("/createProducts")
    public String createProduct(Model model){
        Products product = new Products();
        model.addAttribute("product", product);
        return "web/addProduct";
    }


    @GetMapping("/search")
    public String searchProduct(Model model,@RequestParam(value = "keyword",required = false)String keyword)
    {
        List<Products> product=productService.searchProduct(keyword);
        model.addAttribute("listProducts",product);
        return "web/crud_products";
    }
}