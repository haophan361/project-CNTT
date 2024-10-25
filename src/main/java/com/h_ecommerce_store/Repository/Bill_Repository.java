package com.h_ecommerce_store.Repository;

import com.h_ecommerce_store.Model.Bills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Bill_Repository extends JpaRepository<Bills, Integer>
{
    @Query("SELECT COALESCE(MAX(ID),0)+1 AS newID FROM Bills ")
    int newID();
    @Query("SELECT COUNT(*) FROM Bills b JOIN b.billDetails bd JOIN bd.product p WHERE b.customer.email = :email AND p.ID= :productID")
    Long getNumberTimeBuyPro(@Param("email") String email,@Param("productID") int productID);
    @Query("SELECT b FROM Bills b WHERE b.customer.email= :email")
    List<Bills> getBillByEmail(@Param("email") String email);
    @Query("SELECT b FROM Bills b WHERE b.customer.email= :email AND b.receive_date IS NOT NULL")
    List<Bills> getBillsByEmail_Success(@Param("email") String email);
    @Query("SELECT b FROM Bills b WHERE b.customer.email= :email AND b.status=0")
    List<Bills> getBillsByEmail_Unpaid(@Param("email") String email);
    @Query("SELECT b FROM Bills b WHERE b.customer.email= :email AND b.status=1")
    List<Bills> getBillsByEmail_Paid(@Param("email") String email);
}
