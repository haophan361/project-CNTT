package com.h_ecommerce_store.Service;
import com.h_ecommerce_store.Model.Customers;
import com.h_ecommerce_store.Repository.Customer_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class customer_Service
{
    private final Customer_Repository customer_repository;
    public void insertCustomer(Customers customer)
    {
        customer_repository.save(customer);
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
