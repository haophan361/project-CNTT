package com.h_ecommerce_store.Controller;

import com.h_ecommerce_store.Model.Products;
import com.h_ecommerce_store.Service.imgProduct_Service;
import com.h_ecommerce_store.Service.product_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping
public class products_Controller
{
    @Autowired
    private product_Service productService;
    @Autowired
    private imgProduct_Service img_productService;
    @PostMapping("/admin/saveProducts")
    public String saveProduct(@ModelAttribute("product") Products product, @RequestParam("imageInput") MultipartFile imageFile)
    {
        if (!imageFile.isEmpty())
        {
            String fileName = img_productService.upload(imageFile);
            product.setImage_url(fileName);
        }
        productService.saveProduct(product);
        return "redirect:/admin/crudProducts";
    }
    @PostMapping("/admin/updateProduct")
    public String updateProduct(@ModelAttribute("product") Products product, @RequestParam("imageInput") MultipartFile imageFile)
    {
        if (!imageFile.isEmpty())
        {
            String fileName = img_productService.upload(imageFile);
            product.setImage_url(fileName);
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
