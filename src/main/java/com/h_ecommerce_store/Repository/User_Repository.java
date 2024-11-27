package com.h_ecommerce_store.Repository;

import com.h_ecommerce_store.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface User_Repository extends JpaRepository<Users, String>, JpaSpecificationExecutor<Users>
{
    @Query("SELECT c.phone FROM Users c")
    List<String> listPhone();
    @Query("SELECT c FROM Users c WHERE c.email= :username")
    Users findByEmail(@Param("username") String username);
}
