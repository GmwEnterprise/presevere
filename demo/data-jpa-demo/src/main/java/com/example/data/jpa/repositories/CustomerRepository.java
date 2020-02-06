package com.example.data.jpa.repositories;

import com.example.data.jpa.domain.Customer;
import com.example.data.jpa.domain.EmailAddress;
import org.springframework.data.repository.Repository;

public interface CustomerRepository extends Repository<Customer, Long> {

    Customer findByEmailAddress(EmailAddress email);
}
