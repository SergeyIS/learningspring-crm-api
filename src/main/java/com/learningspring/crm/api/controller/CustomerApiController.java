package com.learningspring.crm.api.controller;

import com.learningspring.crm.api.exception.ApiException;
import com.learningspring.crm.api.exception.BadRequestApiException;
import com.learningspring.crm.api.exception.InternalServerErrorApiException;
import com.learningspring.crm.api.exception.NotFoundApiException;
import com.learningspring.crm.api.model.CustomerModel;
import com.learningspring.crm.api.model.ExceptionModel;
import com.learningspring.crm.data.DataStorage;
import com.learningspring.crm.data.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validator;
import java.util.List;

@EnableAspectJAutoProxy
@RestController
@RequestMapping("/api")
@CrossOrigin
public class CustomerApiController {

    private final DataStorage db;
    private final Validator validator;

    @Autowired
    public CustomerApiController(DataStorage db, Validator validator) {
        this.db = db;
        this.validator = validator;
    }

    @GetMapping(value = {"/1.0/customer/{id}", "/2.0/customer/{id}"})
    public Customer getCustomer(@PathVariable Long id) {
        if (id == null || id < 0) {
            throw new BadRequestApiException();
        }

        Customer customer = db.getCustomerById(id);
        if (customer == null) {
            throw new NotFoundApiException();
        }

        return customer;
    }

    @Deprecated
    @GetMapping(value = {"/1.0/customer/all"})
    public List<Customer> getAllCustomers() {
        List<Customer> customers = db.getAllCustomers();
        if (customers == null || customers.isEmpty()) {
            throw new NotFoundApiException();
        }

        return customers;
    }

    @GetMapping("/2.0/customer/all")
    public List<Customer> getAllCustomers(@RequestParam("page") Long page,
                                          @RequestParam("items") Long items) {
        if (page < 0 || items < 0) {
            throw new BadRequestApiException();
        }

        List<Customer> result = db.getCustomers(page, items);

        return result;
    }


    @PostMapping(value = {"/1.0/customer", "/2.0/customer"})
    public CustomerModel addNewCustomer(@RequestBody CustomerModel model) {
        if (!validator.validate(model).isEmpty()) {
            throw new BadRequestApiException();
        }

        Customer customer = new Customer(model.getFirstName(), model.getLastName(), model.getEmail());
        Long id = db.putCustomer(customer);
        model.setId(id);

        return model;
    }

    @PutMapping(value = {"/1.0/customer", "/2.0/customer"})
    public Customer updateCustomer(@RequestBody CustomerModel model) {
        if (!validator.validate(model).isEmpty()) {
            throw new BadRequestApiException();
        }

        Customer customer = new Customer(model.getFirstName(), model.getLastName(), model.getEmail());
        customer.setId(model.getId());

        db.updateCustomer(customer);

        return customer;
    }

    @DeleteMapping(value = {"/1.0/customer/{id}", "/2.0/customer/{id}"})
    public Customer deleteCustomer(@PathVariable Long id) {
        if (id == null || id < 0) {
            throw new BadRequestApiException();
        }

        Customer customer = db.deleteCustomer(id);

        return customer;
    }


    @ExceptionHandler
    public ResponseEntity<ExceptionModel> err(Exception e) {
        ApiException exp;
        if (e instanceof ApiException) {
            exp = (ApiException) e;
        } else {
            exp = new InternalServerErrorApiException(e.getMessage());
        }

        return new ResponseEntity<>(new ExceptionModel(exp),
                HttpStatus.valueOf(exp.getStatus()));
    }
}
