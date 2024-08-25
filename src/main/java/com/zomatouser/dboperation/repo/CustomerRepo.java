package com.zomatouser.dboperation.repo;

import com.zomatouser.dboperation.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, String> {

}
