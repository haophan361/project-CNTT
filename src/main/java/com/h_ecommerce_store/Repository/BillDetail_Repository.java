package com.h_ecommerce_store.Repository;

import com.h_ecommerce_store.Model.BillDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillDetail_Repository extends JpaRepository<BillDetails, Integer>
{
    @Query("SELECT COALESCE(MAX(ID),0)+1 AS newID FROM BillDetails")
    int newID();
    @Query("SELECT bd FROM BillDetails bd WHERE bd.bill.ID= :billID")
    List<BillDetails> findBillDetailsByBillId(int billID);
}
