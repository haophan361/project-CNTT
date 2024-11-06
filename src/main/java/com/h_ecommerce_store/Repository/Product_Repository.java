package com.h_ecommerce_store.Repository;

import com.h_ecommerce_store.Model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Product_Repository extends JpaRepository<Products,Integer>
{
    @Query("SELECT COALESCE(MAX(ID),0)+1 AS newID FROM Products")
    int newID();
    @Query("SELECT p FROM Products p WHERE  LOWER(p.product_name)  LIKE LOWER(CONCAT('%',:keyword, '%'))")
    List<Products> searchProductsByProduct_name(@Param("keyword") String keyword);

    List<Products> findAllById(Iterable<Integer> ids);

//    @Query("SELECT p FROM Products p WHERE p.product_type = :product_type")
//    Page<Products> searchType(@Param("product_type") String product_type);
    @Query("SELECT DISTINCT(p.product_type) FROM Products p")
    List<String> getTypeProduct();
    @Query("SELECT p.product_name FROM Products p")
    List<String> getProductName();
}
