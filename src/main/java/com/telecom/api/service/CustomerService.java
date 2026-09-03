package com.telecom.api.service;
import com.telecom.api.exception.CustomerNotFoundException;
import com.telecom.api.dto.UsageRequest;
import com.telecom.api.entity.Customer;
import com.telecom.api.entity.Plan;
import com.telecom.api.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import com.telecom.api.entity.Recharge;
import com.telecom.api.repository.RechargeRepository;

import com.telecom.api.repository.PlanRepository;


import java.time.LocalDateTime;
import java.util.List;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RechargeRepository rechargeRepository;
    private final PlanRepository planRepository;

    public CustomerService(
        CustomerRepository customerRepository,
        RechargeRepository rechargeRepository,
        PlanRepository planRepository) {

    this.customerRepository = customerRepository;
    this.rechargeRepository = rechargeRepository;
    this.planRepository = planRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer rechargeCustomer(
        String mobileNumber,
        double amount) {

    if (amount <= 0) {
        throw new IllegalArgumentException(
                "Recharge amount must be greater than 0"
        );
    }

    Customer customer = getCustomerByMobile(mobileNumber);

    // Update balance
    customer.setTalktimeBalance(
            customer.getTalktimeBalance() + amount
    );

    Customer savedCustomer =
            customerRepository.save(customer);

    // Save recharge transaction
    Recharge recharge = new Recharge(
            mobileNumber,
            amount,
            "SUCCESS",
            LocalDateTime.now()
    );

    rechargeRepository.save(recharge);

    return savedCustomer;
}
    public Customer getCustomerByMobile(String mobileNumber) {

        return customerRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
        new CustomerNotFoundException(
                "Customer not found: " + mobileNumber
        ));
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(
            String mobileNumber,
            Customer updatedCustomer) {

        Customer customer = getCustomerByMobile(mobileNumber);

        customer.setName(updatedCustomer.getName());
        customer.setPlan(updatedCustomer.getPlan());
        customer.setDataBalance(updatedCustomer.getDataBalance());
        customer.setTalktimeBalance(
                updatedCustomer.getTalktimeBalance()
        );

        return customerRepository.save(customer);
    }

    public void deleteCustomer(String mobileNumber) {

        Customer customer = getCustomerByMobile(mobileNumber);

        customerRepository.delete(customer);
    }

    public List<Recharge> getRechargeHistory(
        String mobileNumber) {

    // Make sure customer exists
    getCustomerByMobile(mobileNumber);

    return rechargeRepository
            .findByMobileNumber(mobileNumber);
    }

    public Customer assignPlan(
        String mobileNumber,
        Long planId) {

    // Find customer
    Customer customer =
            getCustomerByMobile(mobileNumber);

    // Find plan
    Plan plan = planRepository.findById(planId)
            .orElseThrow(() ->
                    new RuntimeException(
                            "Plan not found: " + planId
                    ));

    // Assign plan to customer
    customer.setPlan(plan.getPlanName());

    // Reset data balance based on the selected plan
    customer.setDataBalance(plan.getDataPerDay());

    return customerRepository.save(customer);
    }

    public Customer recharge(String mobileNumber, Long planId) {

    Customer customer = customerRepository
            .findByMobileNumber(mobileNumber)
            .orElseThrow(() ->
                    new CustomerNotFoundException(
                            "Customer not found: " + mobileNumber
                    )
            );

    Plan plan = planRepository
            .findById(planId)
            .orElseThrow(() ->
                    new RuntimeException(
                            "Plan not found: " + planId
                    )
            );

    customer.setPlan(plan.getPlanName());

    customer.setDataBalance(
            plan.getDataPerDay()
    );

    customer.setTalktimeBalance(
            customer.getTalktimeBalance()
                    + plan.getPrice()
    );

    return customerRepository.save(customer);
    }

    public Customer useServices(String mobileNumber, UsageRequest request) {

    Customer customer = customerRepository.findByMobileNumber(mobileNumber)
            .orElseThrow(() ->
                    new CustomerNotFoundException(
                            "Customer not found: " + mobileNumber));

    double dataUsed = request.getDataUsed() == null
            ? 0.0
            : request.getDataUsed();

    double talktimeUsed = request.getTalktimeUsed() == null
            ? 0.0
            : request.getTalktimeUsed();

    if (dataUsed < 0 || talktimeUsed < 0) {
        throw new IllegalArgumentException(
                "Usage values cannot be negative");
    }

    if (dataUsed > customer.getDataBalance()) {
        throw new IllegalArgumentException(
                "Insufficient data balance");
    }

    if (talktimeUsed > customer.getTalktimeBalance()) {
        throw new IllegalArgumentException(
                "Insufficient talktime balance");
    }

    customer.setDataBalance(
            customer.getDataBalance() - dataUsed);

    customer.setTalktimeBalance(
            customer.getTalktimeBalance() - talktimeUsed);

    return customerRepository.save(customer);
    }
}
