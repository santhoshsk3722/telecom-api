package com.telecom.api.service;

import com.telecom.api.entity.Plan;
import com.telecom.api.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    public Plan getPlanById(Long id) {

        return planRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Plan not found: " + id
                        ));
    }

    public Plan createPlan(Plan plan) {
        return planRepository.save(plan);
    }
}