package com.zomatouser.dboperation.service;

import com.zomatouser.dboperation.exception.ResourceNotFoundException;
import com.zomatouser.dboperation.models.Customer;
import com.zomatouser.dboperation.repo.CustomerRepo;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Customerservice {

    private final CustomerRepo customerRepo;

    public List<Customer> getAllCustomerData(){
        List<Customer> customers = customerRepo.findAll();
        return customers;
    }

    public Customer findCustomerById(String id ){
        Customer customer = customerRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity with id " + id + " not found"));
        return customer;
    }
}
