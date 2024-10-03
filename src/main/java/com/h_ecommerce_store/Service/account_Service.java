package com.h_ecommerce_store.Service;

import com.h_ecommerce_store.Model.Accounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.h_ecommerce_store.Repository.Account_Repository;

import java.util.List;
import java.util.Optional;

@Service
public class account_Service
{
    @Autowired
    Account_Repository account_repository;
    public String checkLogin(Accounts accounts)
    {
        String email=accounts.getEmail();
        Optional<Accounts> checkAccount=account_repository.findById(email);
        if(checkAccount.isPresent())
        {
            String password=checkAccount.get().getPassword();
            if(password.equals(accounts.getPassword()))
            {
                return checkAccount.get().getRole();
            }
        }
        return null;
    }
    public Accounts getAccount(String email)
    {
        Optional<Accounts> account=account_repository.findById(email);
        return account.orElse(null);
    }
    public Accounts insertAccount(Accounts accounts)
    {
        accounts.setRole("ROLE_USER");
        return account_repository.save(accounts);
    }
    public Accounts changePassword(String email, String newPassword)
    {
        Optional<Accounts> account=account_repository.findById(email);
        if(account.isPresent())
        {
            account.get().setPassword(newPassword);
            return account_repository.save(account.get());
        }
        return null;
    }
    public List<String> findAllEmail()
    {
        List<String> listEmail = account_repository.findAllEmail();
        if (listEmail.isEmpty())
        {
            return null;
        }
        return listEmail;
    }
}
