package com.h_ecommerce_store.Service;

import com.h_ecommerce_store.Model.Accounts;
import com.h_ecommerce_store.Model.Customers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.h_ecommerce_store.Repository.Account_Repository;

import java.util.Optional;

@Service
public class account_Service
{
    @Autowired
    Account_Repository account_Repository;
    public boolean checkLogin(Accounts accounts)
    {
        String email=accounts.getEmail();
        Optional<Accounts> checkAccount=account_Repository.findById(email);
        if(checkAccount.isPresent())
        {
            String password=checkAccount.get().getPassword();
            return password.equals(accounts.getPassword());
        }
        else
        {
            return false;
        }
    }
    public void Register(Accounts accounts, Customers customers)
    {

    }
}
