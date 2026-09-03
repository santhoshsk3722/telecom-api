package com.telecom.api.controller;

import com.telecom.api.entity.Customer;
import com.telecom.api.service.CustomerService;

import com.telecom.api.dto.ApiResponse;
import com.telecom.api.dto.CustomerResponse;

import com.telecom.api.dto.RechargeRequest;
import com.telecom.api.dto.UsageRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.telecom.api.entity.Recharge;

import java.util.List;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{mobileNumber}/recharges")
    public List<Recharge> getRechargeHistory(
        @PathVariable String mobileNumber) {

    return customerService.getRechargeHistory(
            mobileNumber
    );
    }

    // Get all customers
    @GetMapping
    public List<Customer> getAllCustomers() {

        return customerService.getAllCustomers();
    }

    // Get customer by mobile number
    @GetMapping("/{mobileNumber}")
    public ApiResponse<CustomerResponse> getCustomer(
        @PathVariable String mobileNumber) {

    Customer customer =
            customerService.getCustomerByMobile(mobileNumber);

    CustomerResponse response =
            new CustomerResponse(
                    customer.getName(),
                    customer.getMobileNumber(),
                    customer.getPlan(),
                    customer.getDataBalance(),
                    customer.getTalktimeBalance()
            );

    return new ApiResponse<>(
            true,
            "Customer retrieved successfully",
            response
    );
    }

    // Create customer
    @PostMapping
    public Customer createCustomer(
            @RequestBody Customer customer) {

        return customerService.createCustomer(customer);
    }

    // Update customer
    @PutMapping("/{mobileNumber}")
    public Customer updateCustomer(
            @PathVariable String mobileNumber,
            @RequestBody Customer customer) {

        return customerService.updateCustomer(
                mobileNumber,
                customer
        );
    }

    // Delete customer
    @DeleteMapping("/{mobileNumber}")
    public ResponseEntity<String> deleteCustomer(
            @PathVariable String mobileNumber) {

        customerService.deleteCustomer(mobileNumber);

        return ResponseEntity.ok(
                "Customer deleted successfully"
        );
    }

    @PostMapping("/{mobileNumber}/recharge")
    public ResponseEntity<ApiResponse<Customer>> recharge(
        @PathVariable String mobileNumber,
        @RequestBody RechargeRequest request) {

    Customer customer = customerService.recharge(
            mobileNumber,
            request.getPlanId()
    );

    return ResponseEntity.ok(
            new ApiResponse<>(
                    true,
                    "Recharge successful",
                    customer
            )
    );
    }

    @PostMapping("/{mobileNumber}/plan/{planId}")
    public Customer assignPlan(
        @PathVariable String mobileNumber,
        @PathVariable Long planId) {

    return customerService.assignPlan(
            mobileNumber,
            planId
    );
    }

    @PostMapping("/{mobileNumber}/usage")
    public ApiResponse<CustomerResponse> useServices(
            @PathVariable String mobileNumber,
            @RequestBody UsageRequest request) {

        Customer customer =
                customerService.useServices(mobileNumber, request);

        CustomerResponse response =
                new CustomerResponse(
                        customer.getName(),
                        customer.getMobileNumber(),
                        customer.getPlan(),
                        customer.getDataBalance(),
                        customer.getTalktimeBalance()
                );

        return new ApiResponse<>(
                true,
                "Usage deducted successfully",
                response
        );
    }
}
