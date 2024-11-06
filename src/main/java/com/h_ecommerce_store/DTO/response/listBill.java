package com.h_ecommerce_store.DTO.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Getter
@Setter
public class listBill
{
    private int billID;
    private String cus_name;
    private int status;
    private int confirm;
    private BigDecimal total;
    private String address;
    private String phone;
    private LocalDateTime purchase_date;
    private LocalDateTime receive_date;
    List<listBillDetail> list_billDetail;
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private String formattedPrice;
    private String formattedPurchase_date;
    private String formattedReceive_date;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy ");
    public listBill(int billID,BigDecimal total,int status,int confirm, LocalDateTime purchase_date, LocalDateTime receive_date,List<listBillDetail> list_billDetail)
    {
        this.billID = billID;
        this.status = status;
        this.confirm = confirm;
        this.total = total;
        this.purchase_date = purchase_date;
        this.receive_date = receive_date;
        this.list_billDetail = list_billDetail;
        this.formattedPrice = decimalFormat.format(total);
        this.formattedPurchase_date = formatter.format(purchase_date);
        if(this.receive_date!=null)
        {
            this.formattedReceive_date = formatter.format(receive_date);
        }
    }
    public listBill(int billID,String cus_name ,BigDecimal total,int status,int confirm,String address,String phone, LocalDateTime purchase_date, LocalDateTime receive_date)
    {
        this.billID = billID;
        this.cus_name = cus_name;
        this.status = status;
        this.confirm = confirm;
        this.total = total;
        this.address = address;
        this.phone = phone;
        this.purchase_date = purchase_date;
        this.receive_date = receive_date;
        this.formattedPrice = decimalFormat.format(total);
        this.formattedPurchase_date = formatter.format(purchase_date);
        if(this.receive_date!=null)
        {
            this.formattedReceive_date = formatter.format(receive_date);
        }
    }
}
