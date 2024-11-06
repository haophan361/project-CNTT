package com.h_ecommerce_store.Service;

import com.h_ecommerce_store.Model.BillDetails;
import com.h_ecommerce_store.Model.Bills;
import com.h_ecommerce_store.Repository.BillDetail_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class billDetail_Service
{
    private final BillDetail_Repository billDetail_repository;
    public void insertBillDetail(Bills bill,BillDetails billDetail)
    {
        int ID= billDetail_repository.newID();
        billDetail.setID(ID);
        billDetail.setBill(bill);
        billDetail_repository.save(billDetail);
    }
    public List<BillDetails> getBillDetailByBillID(int billID)
    {
        return billDetail_repository.getBillDetailsByBillId(billID);
    }
    public void deleteBillDetail(int billDetailID)
    {
        Optional<BillDetails> billDetail = billDetail_repository.findById(billDetailID);
        if(billDetail.isPresent())
        {
            billDetail_repository.deleteById(billDetailID);
        }
    }
    public Double totalRevenueByProductType(String productType)
    {
        return billDetail_repository.totalRevenueByProductType(productType);
    }
    public Double totalRevenueByProduct(String product_name)
    {
        return billDetail_repository.totalRevenueByProduct(product_name);
    }
}
