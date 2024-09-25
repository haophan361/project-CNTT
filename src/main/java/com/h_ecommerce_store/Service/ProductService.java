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

    public void saveProduct(Products products){
        this.productRepository.save(products);
    }

}
