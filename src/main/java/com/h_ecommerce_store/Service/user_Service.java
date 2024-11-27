package com.h_ecommerce_store.Service;
import com.h_ecommerce_store.Entity.Users;
import com.h_ecommerce_store.Repository.Account_Repository;
import com.h_ecommerce_store.Repository.User_Repository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class user_Service
{
    private final User_Repository user_repository;
    private final Account_Repository account_repository;
    public void insertCustomer(Users customer)
    {
        user_repository.save(customer);
    }
    public List<String> listPhone()
    {
        List<String> listPhone = user_repository.listPhone();
        if (listPhone.isEmpty())
        {
            return null;
        }
        return listPhone;
    }
    public void updateProfile(String name,String address,String phone,String username)
    {
         Users user=user_repository.findByEmail(username);
         user.setName(name);
         user.setAddress(address);
         user.setPhone(phone);
         user_repository.save(user);
    }
    public Users getCustomer(String username)
    {
        return user_repository.findByEmail(username);
    }
    public Long totalCustomers()
    {
        return user_repository.count();
    }
    public Page<Users> getUser(String name,String username, List<String> role, String phone, int page, int size)
    {

        Pageable pageable = PageRequest.of(page - 1, size);
        Specification<Users> specification = (Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
        {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.trim().isEmpty())
            {
                Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + name + "%");
                predicates.add(namePredicate);
            }
            Predicate phonePredicate = criteriaBuilder.like(root.get("phone"), "%" + phone + "%");
            predicates.add(phonePredicate);
            List<String> Username_ByRole=new ArrayList<>();
            if(role != null && !role.isEmpty())
            {
                for (String user_role : role)
                {
                    Username_ByRole.addAll(account_repository.findByRole(user_role));
                }
                Predicate rolePredicate = (root.get("email").in(Username_ByRole));
                predicates.add(rolePredicate);
            }
            Predicate usernamePredicate = criteriaBuilder.like(root.get("email"), "%" + username + "%");
            predicates.add(usernamePredicate);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return user_repository.findAll(specification, pageable);
    }
    public Long countUserByRole(String name,String username,String role, String phone)
    {
        Specification<Users> specification = (Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
        {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.trim().isEmpty())
            {
                Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + name + "%");
                predicates.add(namePredicate);
            }
            Predicate phonePredicate = criteriaBuilder.like(root.get("phone"), "%" + phone + "%");
            predicates.add(phonePredicate);
            List<String> Username_ByRole= account_repository.findByRole(role);
            Predicate rolePredicate = (root.get("email").in(Username_ByRole));
            predicates.add(rolePredicate);
            Predicate usernamePredicate = criteriaBuilder.like(root.get("email"), "%" + username + "%");
            predicates.add(usernamePredicate);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return user_repository.count(specification);
    }
}
