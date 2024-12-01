package com.h_ecommerce_store.Controller;

import com.h_ecommerce_store.DTO.response.listBill;
import com.h_ecommerce_store.DTO.response.listBillDetail;
import com.h_ecommerce_store.Entity.BillDetails;
import com.h_ecommerce_store.Entity.Bills;
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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class Manage_bill
{
    private final bill_Service bill_service;
    private final billDetail_Service billDetail_service;
    @GetMapping("/staff/bill")
    public String Manage_Bill(Model model, @RequestParam(value="page", required=false, defaultValue="1") int page,
                              @RequestParam(value = "cus_name",defaultValue = "") String cus_name,
                              @RequestParam(value="status",required = false) List<Integer> status,
                              @RequestParam(value = "confirm",required = false) List<Integer> confirm,
                              @RequestParam(value = "calendar_start",required = false) String timeStart,
                              @RequestParam(value = "calendar_end",required = false) String timeEnd,
                              HttpServletRequest request)
    {
        Page<Bills> bills =bill_service.getBillsByCusName(cus_name,status,confirm,timeStart,timeEnd,page,10);
        List<Bills> billsList=bills.getContent();
        List<listBill> listBills = new ArrayList<>();
        for (Bills bill : billsList)
        {
            listBill list_bill = new listBill(
                    bill.getID(),
                    bill.getName(),
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
        model.addAttribute("cus_name",cus_name);
        model.addAttribute("status",status);
        model.addAttribute("confirm",confirm);
        model.addAttribute("calendar_start",timeStart);
        model.addAttribute("calendar_end",timeEnd);
        model.addAttribute("status_0",bill_service.countBillByStatus(cus_name,0,confirm,timeStart,timeEnd));
        model.addAttribute("status_1",bill_service.countBillByStatus(cus_name,1,confirm,timeStart,timeEnd));
        model.addAttribute("confirm_0",bill_service.countBillByConfirm(cus_name,status,0,timeStart,timeEnd));
        model.addAttribute("confirm_1",bill_service.countBillByConfirm(cus_name,status,1,timeStart,timeEnd));
        String statusParam="";
        if(status!=null && !status.isEmpty())
        {
            String statusValue=status.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("%2C"));
            statusParam="&status=" + statusValue;
        }
        String confirmParam="";
        if(confirm!=null && !confirm.isEmpty())
        {
            String confirmValue = confirm.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("%2C"));
            confirmParam = "&confirm=" + confirmValue;
        }
        String calendarStartParam="";
        if(timeStart!=null)
        {
            calendarStartParam="&calendar_start="+timeStart;
        }
        String calendarEndParam="";
        if(timeEnd!=null)
        {
            calendarEndParam="&calendar_end="+timeEnd;
        }
        model.addAttribute("requestURI", request.getRequestURI()+"?cus_name="+cus_name
                +calendarStartParam+calendarEndParam+statusParam+confirmParam);
        return "staff/bill";
    }
    @GetMapping("/staff/billDetail")
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
                    billDetail.getQuantity(),
                    billDetail.getCost());
            listBillDetails.add(BillDetail);
        }
        Bills bill=bill_service.getBillByID(billID);
        model.addAttribute("confirm",bill.getConfirm());
        model.addAttribute("billID",billID);
        model.addAttribute("listBillDetails",listBillDetails);
        return "staff/billDetail";
    }
    @PostMapping("/staff/confirmBill")
    public String ConfirmBill(@RequestParam("billID") int billID)
    {
        bill_service.confirmBill(billID);
        return "redirect:/staff/bill";
    }
    @PostMapping("/staff/deleteBill")
    public String deleteBill(@RequestParam("selectedBillIDs") List<Integer> selectedBillIds)
    {
        for (Integer billID : selectedBillIds)
        {
            bill_service.deleteBillByID(billID);
        }
        return "redirect:/staff/bill";
    }
}
