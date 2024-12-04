package com.h_ecommerce_store.Controller;

import com.h_ecommerce_store.DTO.response.listBill;
import com.h_ecommerce_store.DTO.response.listBillDetail;
import com.h_ecommerce_store.Util.Load_dataNavbar;
import com.h_ecommerce_store.DTO.request.checkout_Bill;
import com.h_ecommerce_store.DTO.request.checkout_BillDetail;
import com.h_ecommerce_store.Entity.*;
import com.h_ecommerce_store.Service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class bill_Controller
{
    private final account_Service account_service;
    private final cart_Service cart_service;
    private final product_Service product_service;
    private final user_Service user_service;
    private final bill_Service bill_service;
    private final Load_dataNavbar load_dataNavbar;
    @GetMapping("/user/form_BillDetail")
    public String getListBill(@RequestParam("selectedCartIDs") List<Integer> selectedCartIds, Model model)
    {
        load_dataNavbar.load_Navbar(model);
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
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            BigDecimal total=new BigDecimal(0);
            for(checkout_BillDetail billDetail: checkout_bill.getBillDetails())
            {
                billDetail.setFormatted_newPrice(decimalFormat.format(billDetail.getProductNewPrice()));
                total=total.add(billDetail.getProductNewPrice());
            }
            checkout_bill.setFormatted_price(decimalFormat.format(total));
            return "/web/checkout";
        }
        String username=account_service.getLoggedUserName();
        Users customer= user_service.getCustomer(username);
        String cus_name=checkout_bill.getName();
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
        Bills bill=new Bills(customer,cus_name,address,phone,total,status,0);
        bill_service.insertBill(bill,billDetails);
        return "redirect:/user/shoppingCart";
    }
    @GetMapping("/user/Bill")
    public String getBill(Model model)
    {
        load_dataNavbar.load_Navbar(model);
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
        List<Bills> bills = bill_service.getBillsByEmail_statusBill(username,0);
        return requestBill(model, bills);
    }
    @GetMapping("/user/listBill_Paid")
    public String listBill_Paid(Model model)
    {
        String username = account_service.getLoggedUserName();
        List<Bills> bills = bill_service.getBillsByEmail_statusBill(username,1);
        return requestBill(model, bills);
    }
    @GetMapping("/user/listBill_Waiting")
    public String listBill_Waiting(Model model)
    {
        String username = account_service.getLoggedUserName();
        List<Bills> bills = bill_service.getBillsByEmail_Waiting(username);
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
                    bill.getConfirm(),
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
        bill_service.updateStatusBill(ID,1);
        return ResponseEntity.ok("/user/Bill");
    }
    @PostMapping("/user/cancelBill")
    public String cancelBill(@RequestParam("billID") int billID)
    {
        bill_service.cancelBill(billID);
        return "redirect:/user/Bill";
    }
    @GetMapping("/user/reorder/{billID}")
    public String reorderBill(@PathVariable int billID, Principal principal) {
        // Lấy thông tin hóa đơn cũ
        Bills oldBill = bill_service.getBillByID(billID);
        if (oldBill == null) {
            return "redirect:/user/listBill?error=BillNotFound"; // Nếu không tìm thấy hóa đơn
        }

        // Lấy thông tin người dùng hiện tại
        String username = principal.getName();
        Users user = user_service.getCustomer(username);

        // Duyệt qua danh sách chi tiết hóa đơn
        for (BillDetails billDetail : oldBill.getBillDetails()) {
            Products product = billDetail.getProduct();
            int quantity = billDetail.getQuantity();

            // Kiểm tra sản phẩm đã tồn tại trong giỏ hàng hay chưa
            Shopping_Carts existingCart = cart_service.checkExist_Cart(product.getID(), username);
            if (existingCart != null) {
                // Nếu đã tồn tại, cập nhật số lượng
                existingCart.setQuantity(existingCart.getQuantity() + quantity);
                cart_service.updateCart(existingCart);
            } else {
                // Nếu chưa tồn tại, thêm mới sản phẩm vào giỏ hàng
                Shopping_Carts newCart = new Shopping_Carts(quantity, user, product);
                cart_service.addToCart(newCart);
            }
        }

        // Chuyển hướng về giỏ hàng sau khi thêm
        return "redirect:/user/shoppingCart";
    }

}
