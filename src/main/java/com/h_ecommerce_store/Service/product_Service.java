package com.h_ecommerce_store.Service;

import com.h_ecommerce_store.Model.Products;
import com.h_ecommerce_store.Repository.Product_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class product_Service {
    private final Product_Repository productRepository;

    public void deleteProductsById(int ID )
    {
        this.productRepository.deleteById( ID);
    }
    public Products getProductsByID(int ID)
    {
        Optional<Products> optional = productRepository.findById(ID);
        Products products=null;
        if(optional.isPresent())
        {
            products= optional.get();
        }
        else
        {
            throw new RuntimeException("Products not found.");
        }
        return products;
    }
    public List<Products> getAllProducts()
    {
        return productRepository.findAll();
    }

    public Products saveProduct(Products products)
    {
        int newID= productRepository.newID();
        products.setID(newID);
        return productRepository.save(products);
    }
    public Products updateProduct(Products products)
    {
        if(productRepository.existsById(products.getID()))
        {
            return this.productRepository.save(products);
        }
        else
        {
            throw new RuntimeException("Sản phẩm không tồn tại");
        }
    }
    public List<Products> searchProduct(String keyword)
    {
        return productRepository.searchProductsByProduct_name(keyword);
    }
    public void buyProduct(int productID,int quantity)
    {
        Optional<Products> existingProduct = productRepository.findById(productID);
        if(existingProduct.isPresent())
        {
            existingProduct.get().setQuantity(existingProduct.get().getQuantity()-quantity);
            productRepository.save(existingProduct.get());
        }
    }
    public void cancelProduct(int productID,int quantity)
    {
        Optional<Products> existingProduct = productRepository.findById(productID);
        if(existingProduct.isPresent())
        {
            existingProduct.get().setQuantity(existingProduct.get().getQuantity()+quantity);
            productRepository.save(existingProduct.get());
        }
    }
    public Long totalProduct()
    {
        return productRepository.count();
    }
    public List<String> getProductType()
    {
        return productRepository.getTypeProduct();
    }
    public List<String> getProductName()
    {
        return productRepository.getProductName();
    }
}
