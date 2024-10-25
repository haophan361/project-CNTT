package com.h_ecommerce_store.Service;

import com.h_ecommerce_store.Model.BillDetails;
import com.h_ecommerce_store.Model.Bills;
import com.h_ecommerce_store.Repository.Bill_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class bill_Service
{
    private final Bill_Repository bill_repository;
    private final billDetail_Service billDetail_service;
    private final product_Service product_service;
    private final cart_Service cart_service;
    @Transactional
    public void insertBill(Bills bill, List<BillDetails> billDetails)
    {
        int billID=bill_repository.newID();
        bill.setID(billID);
        bill_repository.save(bill);
        BigDecimal cost=new BigDecimal("0");
        for (BillDetails billDetail : billDetails)
        {
            product_service.buyProduct(billDetail.getProduct().getID(), billDetail.getQuantity());
            billDetail_service.insertBillDetail(bill,billDetail);
            cost=cost.add(billDetail.getCost());
            int cartID=cart_service.getCartID(billDetail.getProduct().getID(),bill.getCustomer().getEmail());
            cart_service.deleteCart(cartID);
        }
        bill.setBillDetails(billDetails);
        bill.setCost(cost);
        bill_repository.save(bill);
    }
    public Long getNumberTimeBuyPro(String email,int productID)
    {
        return bill_repository.getNumberTimeBuyPro(email,productID);
    }
    public List<Bills> getBillByEmail(String email)
    {
        return bill_repository.getBillByEmail(email);
    }
    public void updateStatusBill(int billID)
    {
        Optional<Bills> existBill=bill_repository.findById(billID);
        if(existBill.isPresent())
        {
            existBill.get().setStatus(1);
            existBill.get().setReceive_date(LocalDateTime.now());
            bill_repository.save(existBill.get());
        }
    }
    public List<Bills> getBillsByEmail_Success(String email)
    {
        return bill_repository.getBillsByEmail_Success(email);
    }
    public List<Bills> getBillsByEmail_Unpaid(String email)
    {
        return bill_repository.getBillsByEmail_Unpaid(email);
    }
    public List<Bills> getBillsByEmail_Paid(String email)
    {
        return bill_repository.getBillsByEmail_Paid(email);
    }
}
