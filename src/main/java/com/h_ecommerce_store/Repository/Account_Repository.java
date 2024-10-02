package com.h_ecommerce_store.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.h_ecommerce_store.Model.Accounts;
public interface Account_Repository extends JpaRepository<Accounts,String>
{

}
