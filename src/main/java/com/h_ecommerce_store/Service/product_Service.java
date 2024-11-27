package com.h_ecommerce_store.Service;

import com.h_ecommerce_store.Entity.Products;
import com.h_ecommerce_store.Repository.Product_Repository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class product_Service {
    private final Product_Repository productRepository;
    public void deleteProductsById(int ID )
    {
        productRepository.deleteById( ID);
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
    public Page<Products> noBrand_selectByName_Products(String keyword,int page,int size)
    {
        Pageable pageable = PageRequest.of(page-1, size);
        return productRepository.noBrand_selectByName_Products(keyword,pageable);
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
    public Long countProduct_ByProductType(String productType,List<String> brand,String product_name)
    {
        Specification<Products> specification = (Root<Products> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
        {
            List<Predicate> predicates = new ArrayList<>();
            if (product_name != null && !product_name.trim().isEmpty())
            {
                Predicate product_namePredicate = criteriaBuilder.like(root.get("product_name"), "%" + product_name + "%");
                predicates.add(product_namePredicate);
            }
            if (brand != null && !brand.isEmpty())
            {
                Predicate brandPredicate = root.get("brand").in(brand);
                predicates.add(brandPredicate);
            }
            Predicate productTypePredicate = criteriaBuilder.equal(root.get("product_type"), productType);
            predicates.add(productTypePredicate);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return productRepository.count(specification);
    }
    public Long countProductBrand_ByType(String brand,List<String> productType,String product_name)
    {
        Specification<Products> specification = (Root<Products> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
        {
            List<Predicate> predicates = new ArrayList<>();
            if (product_name != null && !product_name.trim().isEmpty())
            {
                Predicate product_namePredicate = criteriaBuilder.like(root.get("product_name"), "%" + product_name + "%");
                predicates.add(product_namePredicate);
            }
            if (productType != null && !productType.isEmpty())
            {
                Predicate brandPredicate = root.get("product_type").in(productType);
                predicates.add(brandPredicate);
            }
            Predicate productTypePredicate = criteriaBuilder.equal(root.get("brand"), brand);
            predicates.add(productTypePredicate);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return productRepository.count(specification);
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
    public List<Products> getRelatedProductByType(String productType,int ID)
    {
        return productRepository.getRelatedProductByType(productType,ID);
    }
    public Page<Products> getProduct(String name, List<String> productType, List<String> brand,String url, int page, int size)
    {

        Pageable pageable = PageRequest.of(page - 1, size);
        Specification<Products> specification = (Root<Products> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
        {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.trim().isEmpty())
            {
                Predicate namePredicate = criteriaBuilder.like(root.get("product_name"), "%" + name + "%");
                predicates.add(namePredicate);
            }
            if (brand != null && !brand.isEmpty())
            {
                Predicate brandPredicate = root.get("brand").in(brand);
                predicates.add(brandPredicate);
            }
            if (productType != null && !productType.isEmpty())
            {
                Predicate productTypePredicate = root.get("product_type").in(productType);
                predicates.add(productTypePredicate);
            }
            if(url.equals("discount"))
            {
                Predicate discountPredicate = criteriaBuilder.greaterThan(root.get("discount"),0 );
                predicates.add(discountPredicate);
                query.orderBy(criteriaBuilder.desc(root.get("discount")));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return productRepository.findAll(specification, pageable);
    }
}
