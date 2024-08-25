package com.zomatouser.dboperation.service;

import com.zomatouser.dboperation.models.Customer;
import com.zomatouser.dboperation.repo.CustomerRepo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Customerservice {

    private final CustomerRepo customerRepo;

    public List<Customer> getAllCustomerData(){
        List<Customer> customers = customerRepo.findAll();
        return customers;
    }
}
