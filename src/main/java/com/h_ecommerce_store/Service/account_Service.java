package com.h_ecommerce_store.Service;

import com.h_ecommerce_store.Model.Accounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.h_ecommerce_store.Repository.Account_Repository;
import java.util.List;
@Service
public class account_Service implements UserDetailsService
{
    @Autowired
    Account_Repository account_repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username)
    {
        Accounts account=account_repository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(
                account.getUsername(),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority(account.getRole()))
        );
    }
    public Accounts getAccount(String username)
    {
        return account_repository.findByUsername(username);
    }
    public Accounts insertAccount(Accounts account)
    {
        String encodedPassword =passwordEncoder.encode(account.getPassword());
        account.setPassword(encodedPassword);
        account.setRole("ROLE_USER");
        return account_repository.save(account);
    }
    public Accounts changePassword(String username, String newPassword)
    {
        Accounts account=account_repository.findByUsername(username);
        String encodedPassword =passwordEncoder.encode(newPassword);
        account.setPassword(encodedPassword);
        return account_repository.save(account);
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
