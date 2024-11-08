package com.h_ecommerce_store.Controller;

import com.h_ecommerce_store.DTO.response.listBill;
import com.h_ecommerce_store.DTO.response.listBillDetail;
import com.h_ecommerce_store.Model.BillDetails;
import com.h_ecommerce_store.Model.Bills;
import com.h_ecommerce_store.Service.billDetail_Service;
import com.h_ecommerce_store.Service.bill_Service;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class Manage_bill
{
    private final bill_Service bill_service;
    private final billDetail_Service billDetail_service;
    @GetMapping("/admin/bill")
    public String searchBillBy_cusName(Model model, @RequestParam(value="page", required=false, defaultValue="1") int page,
                                       HttpServletRequest request)
    {
        Page<Bills> bills =bill_service.getAllBill(page,20);
        List<Bills> billsList=bills.getContent();
        List<listBill> listBills = new ArrayList<>();
        for (Bills bill : billsList)
        {
            listBill list_bill = new listBill(
                    bill.getID(),
                    bill.getCus_name(),
                    bill.getCost(),
                    bill.getStatus(),
                    bill.getConfirm(),
                    bill.getAddress(),
                    bill.getPhone(),
                    bill.getPurchase_date(),
                    bill.getReceive_date()
            );
            listBills.add(list_bill);
        }
        model.addAttribute("listBills",listBills);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bills.getTotalPages());
        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/bill";
    }
    @GetMapping("/admin/searchBillBy_cusName")
    public String Manage_Bill(Model model,@RequestParam(value = "cus_name",required = false) String cus_name,
                              @RequestParam(value="page", required=false, defaultValue="1") int page,
                              HttpServletRequest request)
    {
        Page<Bills> bills =bill_service.getBillsByCusName(cus_name,page,20);
        List<Bills> billsList=bills.getContent();
        List<listBill> listBills = new ArrayList<>();
        for (Bills bill : billsList)
        {
            listBill list_bill = new listBill(
                    bill.getID(),
                    bill.getCus_name(),
                    bill.getCost(),
                    bill.getStatus(),
                    bill.getConfirm(),
                    bill.getAddress(),
                    bill.getPhone(),
                    bill.getPurchase_date(),
                    bill.getReceive_date()
            );
            listBills.add(list_bill);
        }
        model.addAttribute("listBills",listBills);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bills.getTotalPages());
        model.addAttribute("requestURI", request.getRequestURI()+"?cus_name="+cus_name);
        return "admin/bill";
    }
    @GetMapping("/admin/billDetail")
    public String Manage_billDetail(@RequestParam("billID") int billID, Model model)
    {
        List<BillDetails> billDetails=billDetail_service.getBillDetailByBillID(billID);
        List<listBillDetail>listBillDetails =new ArrayList<>();
        for (BillDetails billDetail:billDetails)
        {
            listBillDetail BillDetail=new listBillDetail(
                    billDetail.getID(),
                    billDetail.getProduct().getImage_url(),
                    billDetail.getProduct().getProduct_name(),
                    billDetail.getProduct().getID(),
                    billDetail.getProduct().getQuantity(),
                    billDetail.getCost());
            listBillDetails.add(BillDetail);
        }
        Bills bill=bill_service.getBillByID(billID);
        model.addAttribute("confirm",bill.getConfirm());
        model.addAttribute("billID",billID);
        model.addAttribute("listBillDetails",listBillDetails);
        return "admin/billDetail";
    }
    @PostMapping("/admin/confirmBill")
    public String ConfirmBill(@RequestParam("billID") int billID)
    {
        bill_service.confirmBill(billID);
        return "redirect:/admin/bill";
    }
    @PostMapping("/admin/deleteBill")
    public String deleteBill(@RequestParam("selectedBillIDs") List<Integer> selectedBillIds)
    {
        for (Integer billID : selectedBillIds)
        {
            bill_service.deleteBillByID(billID);
        }
        return "redirect:/admin/bill";
    }
}
