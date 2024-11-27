package com.h_ecommerce_store.Repository;

import com.h_ecommerce_store.Entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface Product_Repository extends JpaRepository<Products,Integer>, JpaSpecificationExecutor<Products>
{
    @Query("SELECT COALESCE(MAX(ID),0)+1 AS newID FROM Products")
    int newID();
    @Query("SELECT p FROM Products p WHERE  LOWER(p.product_name)  LIKE LOWER(CONCAT('%',:keyword, '%'))")
    Page<Products> noBrand_selectByName_Products(@Param("keyword") String keyword, Pageable pageable);
    @Query("SELECT DISTINCT(p.product_type) FROM Products p")
    List<String> getTypeProduct();
    @Query("SELECT DISTINCT(p.brand) FROM Products p")
    List<String> getBrandProduct();
    @Query("SELECT p.product_name FROM Products p")
    List<String> getProductName();
    @Query("SELECT COUNT(*) FROM Products p WHERE p.quantity=0")
    Long countProduct_outOfStock();
    @Query("SELECT p FROM Products p WHERE p.quantity=0 ")
    Page<Products> getLisProduct_outOfStock(Pageable pageable);
    @Query("SELECT COUNT(*) FROM Products p WHERE p.quantity>0")
    Long countProduct_inStock();
    @Query("SELECT p FROM Products p WHERE p.quantity>0 ")
    Page<Products> getLisProduct_inStock(Pageable pageable);
    @Query("SELECT p FROM Products p WHERE p.product_type= :productType")
    Page<Products> noBrand_getListProductByType(@Param("productType") String productType, Pageable pageable);
    @Query("SELECT p FROM Products p WHERE p.product_type= :productType AND p.ID!= :ID")
    List<Products> getRelatedProductByType(@Param("productType") String productType, @Param("ID") Integer ID);
}
