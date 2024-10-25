package com.h_ecommerce_store.Controller;

import com.h_ecommerce_store.DTO.response.listBill;
import com.h_ecommerce_store.DTO.response.listBillDetail;
import com.h_ecommerce_store.Util.Load_dataNavbar;
import com.h_ecommerce_store.DTO.request.checkout_Bill;
import com.h_ecommerce_store.DTO.request.checkout_BillDetail;
import com.h_ecommerce_store.Model.*;
import com.h_ecommerce_store.Service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class bill_Controller
{
    private final account_Service account_service;
    private final cart_Service cart_service;
    private final product_Service product_service;
    private final customer_Service customer_service;
    private final bill_Service bill_service;
    private final Load_dataNavbar load_dataNavbar;
    @GetMapping("/user/form_BillDetail")
    public String getListBill(@RequestParam("selectedCartIDs") List<Integer> selectedCartIds, Model model)
    {
        load_dataNavbar.load_navbarHome(model);
        List<checkout_BillDetail> list_BillDetail=new ArrayList<>();
        for(Integer cartID:selectedCartIds)
        {
            Shopping_Carts cart=cart_service.getCart(cartID);
            checkout_BillDetail billDetail=new checkout_BillDetail(
                    cart.getProduct().getID()
                    ,cart.getProduct().getProduct_name()
                    ,cart.getProduct().getImage_url()
                    ,cart.getProduct().getCost()
                    ,cart.getProduct().getDiscount()
                    ,cart.getQuantity());
            list_BillDetail.add(billDetail);
        }
        checkout_Bill checkout_bill=new checkout_Bill(list_BillDetail);
        model.addAttribute("checkout_Bill",checkout_bill);
        return "/web/checkout";
    }
    @PostMapping("/user/insertBill")
    public String insertBill(@Valid @ModelAttribute("checkout_Bill") checkout_Bill checkout_bill, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "/web/checkout";
        }
        String username=account_service.getLoggedUserName();
        Customers customer=customer_service.getCustomer(username);
        String cus_name=checkout_bill.getCus_name();
        BigDecimal total=checkout_bill.getTotal();

        int status=checkout_bill.getStatus();
        String address=checkout_bill.getCity()+", "
                +checkout_bill.getDistrict()+", "
                +checkout_bill.getWard()+", "
                +checkout_bill.getHouseNo();
        String phone=checkout_bill.getPhone();
        List<checkout_BillDetail> checkout_BillDetails=checkout_bill.getBillDetails();
        List<BillDetails> billDetails=new ArrayList<>();
        for(checkout_BillDetail billDetail:checkout_BillDetails)
        {
            Products product=product_service.getProductsByID(billDetail.getProductID());
            BillDetails bill_detail=new BillDetails(product,billDetail.getProductNewPrice(),billDetail.getQuantity());
            billDetails.add(bill_detail);
        }
        Bills bill=new Bills(customer,cus_name,address,phone,total,status);
        bill_service.insertBill(bill,billDetails);
        return "redirect:/user/shoppingCart";
    }
    @GetMapping("/user/Bill")
    public String getBill()
    {
        return "web/bill";
    }

    @GetMapping("/user/listBill_Success")
    public String listBill_Success(Model model)
    {
        String username = account_service.getLoggedUserName();
        List<Bills> bills = bill_service.getBillsByEmail_Success(username);
        return requestBill(model, bills);
    }
    @GetMapping("/user/listBill_Unpaid")
    public String listBill_Unpaid(Model model)
    {
        String username = account_service.getLoggedUserName();
        List<Bills> bills = bill_service.getBillsByEmail_Unpaid(username);
        return requestBill(model, bills);
    }
    @GetMapping("/user/listBill_Paid")
    public String listBill_Paid(Model model)
    {
        String username = account_service.getLoggedUserName();
        List<Bills> bills = bill_service.getBillsByEmail_Paid(username);
        return requestBill(model, bills);
    }
    @GetMapping("/user/listBill")
    public String listBill(Model model)
    {
        String username = account_service.getLoggedUserName();
        List<Bills> bills = bill_service.getBillByEmail(username);
        return requestBill(model, bills);
    }
    public String requestBill(Model model, List<Bills> bills)
    {
        List<listBill> listBills = new ArrayList<>();
        List<listBillDetail> listBillDetails;
        for (Bills bill : bills)
        {
            listBillDetails = new ArrayList<>();
            for (BillDetails billDetail : bill.getBillDetails())
            {
                listBillDetail listBillDetail = new listBillDetail(
                        billDetail.getProduct().getImage_url(),
                        billDetail.getProduct().getProduct_name(),
                        billDetail.getProduct().getID(),
                        billDetail.getQuantity(),
                        billDetail.getCost(),
                        billDetail.getProduct().getCost()
                );
                listBillDetails.add(listBillDetail);
            }
            listBill list_bill = new listBill(
                    bill.getID(),
                    bill.getCost(),
                    bill.getStatus(),
                    bill.getPurchase_date(),
                    bill.getReceive_date(),
                    listBillDetails
            );
            listBills.add(list_bill);
        }
        model.addAttribute("list_bills", listBills);
        return "fragment/bill_fragment :: listBillFragment";
    }
    @PostMapping("/user/updateStatus/{ID}")
    public ResponseEntity<String> updateStatus(@PathVariable("ID") int ID)
    {
        bill_service.updateStatusBill(ID);
        return ResponseEntity.ok("/user/listBill");
    }
}
