package com.devhoss.app.servicioclientes.service;

import com.devhoss.app.servicioclientes.model.Customer;
import com.devhoss.app.servicioclientes.model.CustomerRegistrationRequest;
import com.devhoss.app.servicioclientes.model.FraudCheckResponse;

import java.util.List;

public interface ICustomerService {

    public FraudCheckResponse registerCustomer(CustomerRegistrationRequest customerRequest);

    public List<Customer> findAll();
    public void findCustomerById(String id);
}
