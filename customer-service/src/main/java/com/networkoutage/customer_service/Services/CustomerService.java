package com.networkoutage.customer_service.Services;



import java.util.List;

import org.springframework.stereotype.Service;

import com.networkoutage.customer_service.entities.Customer;
import com.networkoutage.customer_service.repository.CustomerRepository;



@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

public Customer updateCustomer(Long id, Customer updatedCustomer) {

    Customer existingCustomer = customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found"));

    existingCustomer.setName(updatedCustomer.getName());
    existingCustomer.setEmail(updatedCustomer.getEmail());
    existingCustomer.setPhone(updatedCustomer.getPhone());
    existingCustomer.setAddress(updatedCustomer.getAddress());

    return customerRepository.save(existingCustomer);
}
public void deleteCustomer(Long id) {

    Customer existingCustomer = customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found"));

    customerRepository.delete(existingCustomer);
}
}