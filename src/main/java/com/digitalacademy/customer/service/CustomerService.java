package com.digitalacademy.customer.service;

import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomerList(){
        return customerRepository.findAll();
    }

    public Customer getCustomerId(Long id){
        return  customerRepository.findAllById(id);
    }

    public List<Customer> getCustomerName(String firstname){
        return customerRepository.findByFirstName(firstname);
    }

    public Customer createCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customer){
        return customerRepository.findAllById(id) != null ? customerRepository.save(customer) : null;
    }

    public boolean deleteCustomer(Long id) {
        try {
            customerRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
