package com.h_ecommerce_store.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="supplier_bills")
@Getter
@Setter
public class Supplier_Bills
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private BigDecimal cost;
    private LocalDateTime purchase_date;
    private int status;
    private LocalDateTime receive_date;
    @ManyToOne
    @JoinColumn(name="supplierID")
    private Suppliers supplier;
    @OneToMany(mappedBy = "supplier_bill",fetch = FetchType.LAZY)
    private List<Supplier_BillDetails> supplier_billdetailsList=new ArrayList<>();
    public Supplier_Bills(int ID, BigDecimal cost, LocalDateTime purchase_date, int status, LocalDateTime receive_date)
    {
        this.ID = ID;
        this.cost = cost;
        this.purchase_date = purchase_date;
        this.status = status;
        this.receive_date = receive_date;
    }

    public Supplier_Bills(BigDecimal cost, LocalDateTime purchase_date, int status, LocalDateTime receive_date) {
        this.cost = cost;
        this.purchase_date = purchase_date;
        this.status = status;
        this.receive_date = receive_date;
    }

    public Supplier_Bills()
    {

    }
}
