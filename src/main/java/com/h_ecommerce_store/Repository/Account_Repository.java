package com.h_ecommerce_store.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.h_ecommerce_store.Model.Accounts;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Account_Repository extends JpaRepository<Accounts,String>
{
    @Query("SELECT a.email FROM Accounts a")
    List<String> findAllEmail();
}
