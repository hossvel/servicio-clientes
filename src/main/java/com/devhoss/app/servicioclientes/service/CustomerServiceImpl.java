package com.devhoss.app.servicioclientes.service;

import com.devhoss.app.servicioclientes.model.*;
import com.devhoss.app.servicioclientes.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.env.Environment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CustomerServiceImpl implements ICustomerService{

    @Autowired
    private CustomerRepository icustomerRepository;

    @Autowired
    private RestTemplate clienteRest;

    @Autowired
    private  RabbitMQMessageProducer rabbitMQMessageProducer;

    @Autowired
    private Environment env;
   // @Value("${rabbitmq.exchanges.internal}")
   // private String internalExchange;
    //@Value("${rabbitmq.queues.notification}")
    //private String notificationQueue;
    //@Value("${rabbitmq.routing-keys.internal-notification}")
    //private String internalNotificationRoutingKey;
    @Override
    public List<Customer> findAll() {
        return (List<Customer>) icustomerRepository.findAll();
    }
    @Override
    public FraudCheckResponse registerCustomer(CustomerRegistrationRequest customerRequest) {

        System.out.println("serviceRestTemplate - registerCustomer");
        Map<String, String> pathVariables = new HashMap<String, String>();
        pathVariables.put("id", customerRequest.idType()+customerRequest.idValue());
        FraudCheckResponse fraudCustomerCheckResponse = clienteRest.getForObject(
                "http://localhost:8085/api/v1/fraud-checks/{id}",
                FraudCheckResponse.class,
                pathVariables);

        if(null != fraudCustomerCheckResponse && fraudCustomerCheckResponse.isFraudulentCustomer()){
            log.warn("Customer ID {} is fraudster", customerRequest.idType()+customerRequest.idValue());
            throw new IllegalStateException("Is Fraudster");
        }

        Customer customer = Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .gender(customerRequest.gender())
                .idType(customerRequest.idType())
                .idValue(customerRequest.idValue())
                .phoneNumber(customerRequest.phoneNumber())
                .build();
       // icustomerRepository.saveAndFlush(customer);


        // rabbit
        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to Hossvel...",
                        customer.getFirstName() + customer.getLastName())
        );

        rabbitMQMessageProducer.publish(
                notificationRequest,
                "DemoExchange",
                "DemoRoutingKey"
        );



        return fraudCustomerCheckResponse;
    }


    @Override
    public void findCustomerById(String id) {
        System.out.println("serviceRestTemplate - findAll");
        Map<String, String> pathVariables = new HashMap<String, String>();
        pathVariables.put("id", id.toString());
        FraudCheckResponse fraudCheckResponse = clienteRest.getForObject(
                "http://localhost:8085/api/v1/fraud-checks/{id}",

                FraudCheckResponse.class,
                pathVariables);

    }

}
