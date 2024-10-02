package com.h_ecommerce_store.Service;
import com.h_ecommerce_store.Model.Customers;
import com.h_ecommerce_store.Repository.Account_Repository;
import com.h_ecommerce_store.Repository.Customer_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class customer_Service
{
    @Autowired
    private Customer_Repository customer_repository;
    public Customers insertCustomer(Customers customer)
    {
        int newID= customer_repository.newID();
        customer.setID(newID);
        return customer_repository.save(customer);
    }
    public Customers updateCustomer(Customers customer)
    {
        return customer_repository.save(customer);
    }
}
