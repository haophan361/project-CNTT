package com.h_ecommerce_store.Service;

import com.h_ecommerce_store.Entity.BillDetails;
import com.h_ecommerce_store.Entity.Bills;
import com.h_ecommerce_store.Repository.Bill_Repository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
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
            int cartID=cart_service.getCartID(billDetail.getProduct().getID(),bill.getUser().getEmail());
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
    public Bills getBillByID(int billID)
    {
        Optional<Bills> existBill=bill_repository.findById(billID);
        return existBill.orElse(null);
    }
    public void updateStatusBill(int billID,int status)
    {
        Optional<Bills> existBill=bill_repository.findById(billID);
        if(existBill.isPresent())
        {
            existBill.get().setStatus(status);
            existBill.get().setReceive_date(LocalDateTime.now());
            bill_repository.save(existBill.get());
        }
    }
    public void confirmBill(int billID)
    {
        Optional<Bills> existBill=bill_repository.findById(billID);
        if(existBill.isPresent())
        {
            existBill.get().setConfirm(1);
            existBill.get().setPurchase_date(LocalDateTime.now());
            bill_repository.save(existBill.get());
        }
    }
    public List<Bills> getBillsByEmail_Success(String email)
    {
        return bill_repository.getBillsByEmail_Success(email);
    }
    public List<Bills> getBillsByEmail_statusBill(String email,int status)
    {
        return bill_repository.getBillsByEmail_statusBill(email,status);
    }
    public List<Bills> getBillsByEmail_Waiting(String email)
    {
        return bill_repository.getBillsByEmail_Waiting(email);
    }
    public void cancelBill(int billID)
    {
        Optional<Bills> existBill=bill_repository.findById(billID);
        if(existBill.isPresent())
        {
            List<BillDetails> billDetails=billDetail_service.getBillDetailByBillID(billID);
            for (BillDetails billDetail : billDetails)
            {
                product_service.cancelProduct(billDetail.getProduct().getID(),
                        billDetail.getQuantity());
                billDetail_service.deleteBillDetail(billDetail.getID());
            }
            bill_repository.delete(existBill.get());
        }
    }
    public void deleteBillByID(int billID)
    {
        Optional<Bills> existBill=bill_repository.findById(billID);
        existBill.ifPresent(bill_repository::delete);
    }
    public Double totalRevenue()
    {
        return bill_repository.getTotalCost();
    }
    public Double getRevenue_Time(int year,int month)
    {
        LocalDateTime timeStart = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime timeEnd = timeStart.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);
        return bill_repository.getRevenueByTime(timeStart,timeEnd);
    }
    public Long CountBill_NotConfirmOrNotReceive(String email)
    {
        return bill_repository.CountBill_NotConfirmOrNotReceive(email);
    }
    public Page<Bills> getBillsByCusName(String name, List<Integer> status, List<Integer> confirm,String timeStart,String timeEnd, int page, int size)
    {
        Pageable pageable = PageRequest.of(page - 1, size);
        Specification<Bills> specification = (Root<Bills> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
        {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.trim().isEmpty())
            {
                Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + name + "%");
                predicates.add(namePredicate);
            }
            if (status != null && !status.isEmpty())
            {
                Predicate statusPredicate = root.get("status").in(status);
                predicates.add(statusPredicate);
            }
            if (confirm != null && !confirm.isEmpty())
            {
                Predicate confirmPredicate = root.get("confirm").in(confirm);
                predicates.add(confirmPredicate);
            }
            if (timeStart != null && !timeStart.trim().isEmpty() && timeEnd != null && !timeEnd.trim().isEmpty())
            {
                LocalDateTime startDate = LocalDateTime.parse(timeStart + "T00:00:00");
                LocalDateTime endDate = LocalDateTime.parse(timeEnd + "T23:59:59");
                Predicate timeStartPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("purchase_date"),startDate);
                Predicate timeEndPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("purchase_date"),endDate);
                predicates.add(timeStartPredicate);
                predicates.add(timeEndPredicate);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return bill_repository.findAll(specification, pageable);
    }
    public Long countBillByConfirm(String name, List<Integer> status, int confirm,String timeStart,String timeEnd)
    {
        Specification<Bills> specification = (Root<Bills> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
        {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.trim().isEmpty())
            {
                Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + name + "%");
                predicates.add(namePredicate);
            }
            if (status != null && !status.isEmpty())
            {
                Predicate statusPredicate = root.get("status").in(status);
                predicates.add(statusPredicate);
            }
            if (timeStart != null && !timeStart.trim().isEmpty() && timeEnd != null && !timeEnd.trim().isEmpty())
            {
                LocalDateTime startDate = LocalDateTime.parse(timeStart + "T00:00:00");
                LocalDateTime endDate = LocalDateTime.parse(timeEnd + "T23:59:59");
                Predicate timeStartPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("purchase_date"),startDate);
                Predicate timeEndPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("purchase_date"),endDate);
                predicates.add(timeStartPredicate);
                predicates.add(timeEndPredicate);
            }
            Predicate confirmPredicate = criteriaBuilder.equal(root.get("confirm"), confirm);
            predicates.add(confirmPredicate);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return bill_repository.count(specification);
    }
    public Long countBillByStatus(String name, int status, List<Integer> confirm,String timeStart,String timeEnd)
    {
        Specification<Bills> specification = (Root<Bills> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
        {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.trim().isEmpty())
            {
                Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + name + "%");
                predicates.add(namePredicate);
            }
            if (confirm != null && !confirm.isEmpty())
            {
                Predicate confirmPredicate = root.get("confirm").in(confirm);
                predicates.add(confirmPredicate);
            }
            if (timeStart != null && !timeStart.trim().isEmpty() && timeEnd != null && !timeEnd.trim().isEmpty())
            {
                LocalDateTime startDate = LocalDateTime.parse(timeStart + "T00:00:00");
                LocalDateTime endDate = LocalDateTime.parse(timeEnd + "T23:59:59");
                Predicate timeStartPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("purchase_date"),startDate);
                Predicate timeEndPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("purchase_date"),endDate);
                predicates.add(timeStartPredicate);
                predicates.add(timeEndPredicate);
            }
            Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), status);
            predicates.add(statusPredicate);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return bill_repository.count(specification);
    }
}
