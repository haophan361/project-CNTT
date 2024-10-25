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
    private int status;
    private BigDecimal total;
    private LocalDateTime purchase_date;
    private LocalDateTime receive_date;
    List<listBillDetail> list_billDetail;
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private String formattedPrice;
    private String formattedPurchase_date;
    private String formattedReceive_date;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy ");
    public listBill(int billID, BigDecimal total,int status, LocalDateTime purchase_date, LocalDateTime receive_date,List<listBillDetail> list_billDetail)
    {
        this.billID = billID;
        this.status = status;
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
}
