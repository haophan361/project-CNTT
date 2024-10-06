package com.h_ecommerce_store.Service;

import com.h_ecommerce_store.Model.Products;
import com.h_ecommerce_store.Repository.Product_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class product_Service {
    @Autowired
    private Product_Repository productRepository;

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
}
