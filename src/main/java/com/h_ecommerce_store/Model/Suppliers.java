package com.h_ecommerce_store.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="suppliers")
@Getter
@Setter
public class Suppliers
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String supplier_name;
    private String address;
    private String phone;
    @OneToMany(mappedBy= "supplier",fetch = FetchType.LAZY)
    private List<Supplier_Bills> supplierBillsList=new ArrayList<>();
    public Suppliers(int ID, String supplier_name, String address, String phone)
    {
        this.ID = ID;
        this.supplier_name = supplier_name;
        this.address = address;
        this.phone = phone;
    }

    public Suppliers(String supplier_name, String address, String phone)
    {
        this.supplier_name = supplier_name;
        this.address = address;
        this.phone = phone;
    }

    public Suppliers()
    {

    }
}
