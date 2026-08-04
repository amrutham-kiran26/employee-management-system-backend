package com.kiran.ems.service.impl;

import com.kiran.ems.dto.DashboardResponse;
import com.kiran.ems.repository.EmployeeRepository;
import com.kiran.ems.repository.UserRepository;
import com.kiran.ems.service.DashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    private static final Logger logger =
            LoggerFactory.getLogger(DashboardServiceImpl.class);

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public DashboardServiceImpl(EmployeeRepository employeeRepository,
                                UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DashboardResponse getDashboardData() {

        logger.info("Fetching dashboard statistics.");

        long totalEmployees = employeeRepository.count();
        long activeEmployees = employeeRepository.countByStatus("ACTIVE");
        long inactiveEmployees = employeeRepository.countByStatus("INACTIVE");

        long totalUsers = userRepository.count();
        long activeUsers = userRepository.countByStatus("ACTIVE");
        long inactiveUsers = userRepository.countByStatus("INACTIVE");

        logger.info("Dashboard statistics fetched successfully.");

        return DashboardResponse.builder()
                .totalEmployees(totalEmployees)
                .activeEmployees(activeEmployees)
                .inactiveEmployees(inactiveEmployees)
                .totalUsers(totalUsers)
                .activeUsers(activeUsers)
                .inactiveUsers(inactiveUsers)
                .build();
    }
}