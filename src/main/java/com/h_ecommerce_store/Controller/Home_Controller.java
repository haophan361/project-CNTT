package com.h_ecommerce_store.Controller;

import com.h_ecommerce_store.Model.Products;
import com.h_ecommerce_store.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping
public class Home_Controller {
    @Autowired
    private ProductService productService;
    @GetMapping("/")
    public String home(Model model)
    {
        model.addAttribute("listProducts", productService.getAllProducts());
        return "web/product";
    }

    @GetMapping("/createProducts")
    public String createProduct(Model model){
        Products product = new Products();
        model.addAttribute("product", product);

        model.addAttribute("actionUrl","saveProducts");
        return "web/addProduct";
    }

    @PostMapping("/saveProducts")
    public String saveProduct(@ModelAttribute("product") Products product, @RequestParam("imageInput") MultipartFile imageFile) {
            if (!imageFile.isEmpty()) {
                String fileName = imageFile.getOriginalFilename();
                product.setImage_url(fileName);
            }
            productService.saveProduct(product); // Lưu sản phẩm
        return "redirect:/"; // Chuyển hướng đến trang chủ
    }


}
