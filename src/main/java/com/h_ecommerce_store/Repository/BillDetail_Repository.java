package com.h_ecommerce_store.Repository;

import com.h_ecommerce_store.Model.BillDetails;
import com.h_ecommerce_store.Model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillDetail_Repository extends JpaRepository<BillDetails, Integer>
{
    @Query("SELECT COALESCE(MAX(ID),0)+1 AS newID FROM BillDetails")
    int newID();
    @Query("SELECT bd FROM BillDetails bd WHERE bd.bill.ID= :billID")
    List<BillDetails> getBillDetailsByBillId(int billID);
    @Query("SELECT SUM(bd.cost) FROM BillDetails bd WHERE bd.product.product_type= :productType")
    Double totalRevenueByProductType(String productType);
    @Query("SELECT SUM(bd.cost) FROM BillDetails bd WHERE bd.product.product_name= :product_name")
    Double totalRevenueByProduct(String product_name);
    @Query("SELECT bd.product, COUNT(bd.product) AS quantity  FROM BillDetails bd GROUP BY bd.product ORDER BY quantity DESC")
    List<Products> TopProductsSeller();
}
