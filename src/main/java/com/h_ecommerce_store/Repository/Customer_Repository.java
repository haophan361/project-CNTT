package com.h_ecommerce_store.Repository;

import com.h_ecommerce_store.Model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Customer_Repository extends JpaRepository<Customers, Integer>
{
    @Query("SELECT COALESCE(MAX(ID),0)+1 AS newID FROM Customers")
    int newID();
}
