package com.networkoutage.customer_service.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.networkoutage.customer_service.entities.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}