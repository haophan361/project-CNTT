package com.h_ecommerce_store.Service;

import com.h_ecommerce_store.Model.Products;
import com.h_ecommerce_store.Repository.Product_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private Product_Repository productRepository;

    public List<Products> getAllProducts(){
        return productRepository.findAll();
    }

    public Products saveProduct(Products products){
        int newID= productRepository.newID();
        products.setID(newID);
        return productRepository.save(products);
    }

}
