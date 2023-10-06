package com.devhoss.app.servicioclientes.controller;

import com.devhoss.app.servicioclientes.model.Customer;
import com.devhoss.app.servicioclientes.model.CustomerRegistrationRequest;
import com.devhoss.app.servicioclientes.model.FraudCheckResponse;
import com.devhoss.app.servicioclientes.service.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("api/v1/customers")
@RestController
public class CustomerController {

    @Autowired
    private ICustomerService icustomerService;

    @GetMapping
    public List<Customer> findAll(){
        return icustomerService.findAll();
    }

    @PostMapping
    public FraudCheckResponse registerCustomer(@RequestBody CustomerRegistrationRequest customerRequest) {
        log.info("Intercept request register customer {}", customerRequest);
      return  icustomerService.registerCustomer(customerRequest);
    }
}
