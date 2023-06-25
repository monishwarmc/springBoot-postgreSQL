package com.mm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("/customers")
public class Main {

    private final CustomerRepository customerRepository;
    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Customer> getCustoms(){
        return customerRepository.findAll();
    }

    @GetMapping("{id}")
    public Optional<Customer> getCustomsById(@PathVariable("id") int id){
        return customerRepository.findById(id);
    }

    record add(
        String name,
        int age,
        String email
    ){}

    @PostMapping
    public void addC(@RequestBody add cust){
        Customer cutom = new Customer();
        cutom.setName(cust.name);
        cutom.setAge(cust.age);
        cutom.setEmail(cust.email);
        customerRepository.save(cutom);
        System.out.println("added" + cust.name);
    }

    @DeleteMapping("{id}")
    public void delC(@PathVariable("id") int id){
        customerRepository.deleteById(id);
    }

    @PutMapping("{id}")
    public void updateCust(@PathVariable("id") int id,
    @RequestBody add cust) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            if(cust.name != null){
                customer.setName(cust.name);
            }
            if(cust.age != 0){
                customer.setAge(cust.age);
            }
            if(cust.email != null){
                customer.setEmail(cust.email);
            }
            customerRepository.save(customer);
            
            System.out.println("Customer updated: " + customer);
        } else {
            System.out.println("Customer not found with ID: " + id);
        }
    }
}