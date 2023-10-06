package com.devhoss.app.servicioclientes.repository;

import com.devhoss.app.servicioclientes.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findAllById(Integer id);
}