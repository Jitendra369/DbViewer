package com.zomatouser.dboperation.controller;

import com.zomatouser.dboperation.dto.DatabaseNameDto;
import com.zomatouser.dboperation.dto.TableConstraintInfo;
import com.zomatouser.dboperation.dto.TableDescInfoBean;
import com.zomatouser.dboperation.models.Customer;
import com.zomatouser.dboperation.service.Customerservice;
import com.zomatouser.dboperation.service.DataBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/db")
@RequiredArgsConstructor
public class DatabaseController {

    private final DataBaseService dataBaseService;
    private final Customerservice customerservice;

    @GetMapping("/dbNames")
    public List<DatabaseNameDto> getAllDataBaseName(){
        List<DatabaseNameDto> allDBNames = dataBaseService.getAllDBNames();
        return allDBNames;
    }

    @GetMapping("/allTables")
    public List<String> getAllTablesNames(){
        return dataBaseService.getAllTableNames();
    }

    @GetMapping("/all/customers")
    public List<Customer> getAllCustomers(){
        return Optional.ofNullable(customerservice.getAllCustomerData()).orElseGet(ArrayList::new);
    }

    @GetMapping("/tableInfo")
    public List<Map<String, List<TableDescInfoBean>>> getTableInfo(){
        return Optional.ofNullable(dataBaseService.getTableInformation()).orElseGet(ArrayList::new);
    }

    @GetMapping("/constraint")
    public List<TableConstraintInfo> getConstraint(){
        return dataBaseService.getConstraintValue();
    }

    @GetMapping("/customer/{id}")
    public Customer getCustomerById(@PathVariable String id){
        return customerservice.findCustomerById(id);
    }
}
