package com.h_ecommerce_store.Controller;

import com.h_ecommerce_store.Model.Products;
import com.h_ecommerce_store.Service.file_Service;
import com.h_ecommerce_store.Service.product_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RequiredArgsConstructor
@Controller
@RequestMapping
public class products_Controller
{
    private final product_Service productService;
    private final file_Service file_Service;
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
        productService.saveProduct(product);
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
        productService.updateProduct(product);

        return "redirect:/admin/crudProducts";
    }

    @GetMapping("/admin/editProduct/{id}")
    public String showFromForUpdate(@PathVariable(value = "id") int id, Model model)
    {
        Products product = productService.getProductsByID(id);
        model.addAttribute("product",product);
        return "admin/updateProduct";
    }
    @GetMapping("/admin/deleteProduct/{id}")
    public String deleteProduct(@PathVariable (value = "id") int id)
    {
        this.productService.deleteProductsById(id);
        return "redirect:/admin/crudProducts";
    }
    @GetMapping("/admin/crudProducts")
    public String crudProducts(Model model)
    {
        model.addAttribute("listProducts", productService.getAllProducts());
        return "admin/crud_products";
    }
    @GetMapping("/admin/createProducts")
    public String createProduct(Model model)
    {
        Products product = new Products();
        model.addAttribute("product", product);
        return "admin/addProduct";
    }
    @GetMapping("/admin/search")
    public String searchProduct(Model model,@RequestParam(value = "keyword",required = false)String keyword)
    {
        List<Products> product=productService.searchProduct(keyword);
        model.addAttribute("listProducts",product);
        return "admin/crud_products";
    }
}
