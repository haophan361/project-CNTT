package com.h_ecommerce_store.Service;

import com.h_ecommerce_store.Entity.Accounts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.h_ecommerce_store.Repository.Account_Repository;
import java.util.List;
@Service
@RequiredArgsConstructor
public class account_Service implements UserDetailsService
{
    private final Account_Repository account_repository;
    private final PasswordEncoder passwordEncoder;
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
    public void insertAccount(Accounts account)
    {
        String encodedPassword =passwordEncoder.encode(account.getPassword());
        account.setPassword(encodedPassword);
        account.setRole("ROLE_USER");
        account_repository.save(account);
    }
    public void changePassword(String username, String newPassword)
    {
        Accounts account=account_repository.findByUsername(username);
        String encodedPassword =passwordEncoder.encode(newPassword);
        account.setPassword(encodedPassword);
        account_repository.save(account);
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
    public String getLoggedUserName()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated())
        {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails)
            {
                return ((UserDetails) principal).getUsername();
            }
            else
            {
                return principal.toString();
            }
        }
        return null;
    }
    public String getRoleByUsername(String username)
    {
        Accounts accounts =account_repository.findByUsername(username);
        return accounts.getRole();
    }
    public void changeRole(String username,String role)
    {
        Accounts account=account_repository.findByUsername(username);
        account.setRole(role);
        account_repository.save(account);
    }
}
