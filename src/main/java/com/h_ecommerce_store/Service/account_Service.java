package com.h_ecommerce_store.Service;

import com.h_ecommerce_store.Model.Accounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import com.h_ecommerce_store.Repository.Account_Repository;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;
import java.util.Optional;

@Service
public class account_Service implements UserDetailsService
{
    @Autowired
    Account_Repository account_repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<Accounts> account=account_repository.findById(username);
        if (account.isPresent())
        {
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(account.get().getRole()));
            return new User(account.get().getUsername(), account.get().getPassword(), authorities);
        }
        throw new UsernameNotFoundException("Tài khoản: " + username+" không tồn tại");
    }
    public Accounts getAccount(String username)
    {
        Optional<Accounts> account=account_repository.findById(username);
        return account.orElse(null);
    }
    public Accounts insertAccount(Accounts account)
    {
        account.setRole("ROLE_USER");
        return account_repository.save(account);
    }
    public Accounts changePassword(String username, String newPassword)
    {
        Optional<Accounts> account=account_repository.findById(username);
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
