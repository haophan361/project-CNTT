package com.h_ecommerce_store.Service;

import com.h_ecommerce_store.Model.BillDetails;
import com.h_ecommerce_store.Model.Bills;
import com.h_ecommerce_store.Repository.BillDetail_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
