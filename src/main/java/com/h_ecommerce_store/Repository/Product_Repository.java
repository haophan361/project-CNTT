package com.h_ecommerce_store.Repository;

import com.h_ecommerce_store.Model.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Product_Repository extends JpaRepository<Products,Integer>
{
    @Query("SELECT COALESCE(MAX(ID),0)+1 AS newID FROM Products")
    int newID();
}
