package com.h_ecommerce_store.Service;
import com.h_ecommerce_store.Model.Customers;
import com.h_ecommerce_store.Repository.Account_Repository;
import com.h_ecommerce_store.Repository.Customer_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.PropertyPermission;

@Service
public class customer_Service
{
    @Autowired
    private Customer_Repository customer_repository;
    public Customers insertCustomer(Customers customer)
    {
        return customer_repository.save(customer);
    }
    public List<String> listPhone()
    {
        List<String> listPhone = customer_repository.listPhone();
        if (listPhone.isEmpty())
        {
            return null;
        }
        return listPhone;
    }
    public void updateProfile(String name,String address,String phone,String username)
    {
         customer_repository.updateProfile(name,address,phone,username);
    }
    public Customers getCustomer(String username)
    {
        return customer_repository.findByEmail(username);
    }
}
