package com.h_ecommerce_store.Service;

import com.h_ecommerce_store.Model.Products;
import com.h_ecommerce_store.Repository.Product_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public void saveProduct(Products products)
    {
        int newID= productRepository.newID();
        products.setID(newID);
        productRepository.save(products);
    }
    public void updateProduct(Products products)
    {
        if(productRepository.existsById(products.getID()))
        {
            this.productRepository.save(products);
        }
        else
        {
            throw new RuntimeException("Sản phẩm không tồn tại");
        }
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
    public long totalProduct()
    {
        return productRepository.count();
    }
    public long countProduct_inStock()
    {
        return productRepository.countProduct_inStock();
    }
    public long countProduct_outOfStock()
    {
        return productRepository.countProduct_outOfStock();
    }
    public Page<Products> getAllProducts(int page, int size)
    {
        Pageable pageable = PageRequest.of(page-1, size);
        return productRepository.findAll(pageable);
    }
    public Page<Products> getListProductByBrands(List<String> brands,int page, int size)
    {
        Pageable pageable = PageRequest.of(page-1, size);
        return productRepository.getListProductByBrands(brands,pageable);
    }
    public Page<Products> noBrand_selectByName_Products(String keyword,int page,int size)
    {
        Pageable pageable = PageRequest.of(page-1, size);
        return productRepository.noBrand_selectByName_Products(keyword,pageable);
    }
    public Page<Products> selectByName_Products(String keyword,List<String> brands,int page,int size)
    {
        Pageable pageable = PageRequest.of(page-1, size);
        return productRepository.selectByName_Products(keyword,brands,pageable);
    }
    public List<String> getProductType()
    {
        return productRepository.getTypeProduct();
    }
    public List<String> getProductBrand()
    {
        return productRepository.getBrandProduct();
    }
    public List<String> getProductName()
    {
        return productRepository.getProductName();
    }
    public Long countProduct_ByProductType(String productType)
    {
        return productRepository.countProduct_ByProductType(productType);
    }
    public Long countProductBrand_ByType(String brand,String productType)
    {
        return productRepository.countProductBrand_ByType(brand,productType);
    }
    public Page<Products> getLisProduct_inStock(int page,int size)
    {
        Pageable pageable = PageRequest.of(page-1, size);
        return productRepository.getLisProduct_inStock(pageable);
    }
    public Page<Products> getLisProduct_outOfStock(int page,int size)
    {
        Pageable pageable = PageRequest.of(page-1, size);
        return productRepository.getLisProduct_outOfStock(pageable);
    }
    public Page<Products> noBrand_getListProductByType(String productType,int page,int size)
    {
        Pageable pageable = PageRequest.of(page-1, size);
        return productRepository.noBrand_getListProductByType(productType,pageable);
    }
    public Page<Products> getListProductByType(String productType,List<String> brands,int page,int size)
    {
        Pageable pageable = PageRequest.of(page-1, size);
        return productRepository.getListProductByType(productType,brands,pageable);
    }
    public Page<Products> noBrand_getLisProductDiscount(int page,int size)
    {
        Pageable pageable = PageRequest.of(page-1, size);
        return productRepository.noBrand_getLisProductDiscount(pageable);
    }
    public Page<Products> getListProduct_Discount(List<String> brands,int page,int size)
    {
        Pageable pageable = PageRequest.of(page-1, size);
        return productRepository.getLisProductDiscount(brands,pageable);
    }
}
