package com.h_ecommerce_store.Repository;

import com.h_ecommerce_store.Entity.Bills;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
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
    @Query("SELECT b FROM Bills b WHERE b.customer.email= :email AND b.status= :status")
    List<Bills> getBillsByEmail_statusBill(@Param("email") String email,@Param("status") int status);
    @Query("SELECT b FROM Bills b WHERE b.customer.email= :email AND b.confirm= 0")
    List<Bills> getBillsByEmail_Waiting(@Param("email") String email);
    @Query("SELECT SUM(b.cost) FROM Bills b")
    Double getTotalCost();
    @Query("SELECT SUM(b.cost) FROM Bills b WHERE b.purchase_date >= :timeStart and b.purchase_date <= :timeEnd")
    Double getRevenueByTime(LocalDateTime timeStart, LocalDateTime timeEnd);
    @Query("SELECT b FROM Bills b WHERE LOWER(b.cus_name) LIKE LOWER(CONCAT('%',:cus_name, '%'))")
    Page<Bills> getBillsByCusName(@Param("cus_name") String cus_name, Pageable pageable);
}
