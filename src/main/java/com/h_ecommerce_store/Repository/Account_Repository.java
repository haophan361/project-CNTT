package com.h_ecommerce_store.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.h_ecommerce_store.Entity.Accounts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Account_Repository extends JpaRepository<Accounts,String>
{
    @Query("SELECT a.username FROM Accounts a")
    List<String> findAllEmail();
    @Query("SELECT a FROM Accounts  a WHERE a.username = :username")
    Accounts findByUsername(@Param("username")String username);
}
