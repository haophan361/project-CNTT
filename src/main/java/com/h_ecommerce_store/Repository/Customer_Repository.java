package com.h_ecommerce_store.Repository;

import com.h_ecommerce_store.Model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface Customer_Repository extends JpaRepository<Customers, String>
{
    @Query("SELECT c.phone FROM Customers c")
    List<String> listPhone();
    @Modifying
    @Transactional
    @Query("UPDATE Customers c SET c.cus_name= :name,c.address= :address,c.phone= :phone WHERE c.email= :username")
    void updateProfile(@Param("name") String name, @Param("address") String address, @Param("phone") String phone, @Param("username") String username);
    @Query("SELECT c FROM Customers c WHERE c.email= :username")
    Customers findByEmail(@Param("username") String username);
}
