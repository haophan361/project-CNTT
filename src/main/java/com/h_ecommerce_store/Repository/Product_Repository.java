package com.h_ecommerce_store.Repository;

import com.h_ecommerce_store.Entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Query("SELECT p FROM Products p WHERE  LOWER(p.product_name)  LIKE LOWER(CONCAT('%',:keyword, '%')) AND p.brand IN :brands")
    Page<Products> selectByName_Products(@Param("keyword") String keyword,@Param("brands") List<String> brands, Pageable pageable);
    @Query("SELECT p FROM Products p WHERE  LOWER(p.product_name)  LIKE LOWER(CONCAT('%',:keyword, '%'))")
    Page<Products> noBrand_selectByName_Products(@Param("keyword") String keyword, Pageable pageable);
    @Query("SELECT DISTINCT(p.product_type) FROM Products p")
    List<String> getTypeProduct();
    @Query("SELECT DISTINCT(p.brand) FROM Products p")
    List<String> getBrandProduct();
    @Query("SELECT p.product_name FROM Products p")
    List<String> getProductName();
    @Query("SELECT COUNT(*) FROM Products p WHERE p.brand LIKE CONCAT('%',:brand,'%') AND p.product_type LIKE CONCAT('%',:productType,'%')")
    Long countProductBrand_ByType(@Param("brand") String brand, @Param("productType") String productType);
    @Query("SELECT COUNT(*) FROM Products p WHERE p.product_type= :productType")
    Long countProduct_ByProductType(@Param("productType") String productType);
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
    @Query("SELECT p FROM Products p WHERE p.product_type= :productType AND p.brand in :brands")
    Page<Products> getListProductByType(@Param("productType") String productType,@Param("brands") List<String> brands, Pageable pageable);
    @Query("SELECT p FROM Products p WHERE p.discount >0 ORDER BY p.discount DESC")
    Page<Products> noBrand_getLisProductDiscount(Pageable pageable);
    @Query("SELECT p FROM Products p WHERE p.discount >0 AND p.brand in :brands ORDER BY p.discount DESC")
    Page<Products> getLisProductDiscount(@Param("brands") List<String> brands,Pageable pageable);
    @Query("SELECT p FROM Products p WHERE p.brand IN :brands")
    Page<Products> getListProductByBrands(@Param("brands") List<String> brands, Pageable pageable);

}
