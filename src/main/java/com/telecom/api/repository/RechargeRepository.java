package com.telecom.api.repository;

import com.telecom.api.entity.Recharge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RechargeRepository
        extends JpaRepository<Recharge, Long> {

    List<Recharge> findByMobileNumber(String mobileNumber);
}