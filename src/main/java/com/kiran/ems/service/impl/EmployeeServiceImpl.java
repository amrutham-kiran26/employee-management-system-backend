package com.kiran.ems.service.impl;

import com.kiran.ems.entity.Employee;
import com.kiran.ems.exception.BadRequestException;
import com.kiran.ems.exception.ResourceNotFoundException;
import com.kiran.ems.mapper.EmployeeMapper;
import com.kiran.ems.repository.EmployeeRepository;

import com.kiran.ems.dto.EmployeeRequest;
import com.kiran.ems.dto.EmployeeResponse;
import com.kiran.ems.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {

        logger.info("Creating employee with Employee ID: {}",
                employeeRequest.getEmployeeId());

        // Check if Employee ID already exists
        if (employeeRepository.existsByEmployeeId(employeeRequest.getEmployeeId())) {

            logger.warn("Employee ID already exists: {}",
                    employeeRequest.getEmployeeId());

            throw new BadRequestException(
                    "Employee ID already exists: " + employeeRequest.getEmployeeId());
        }

        // Check if Email already exists
        if (employeeRepository.existsByEmail(employeeRequest.getEmail())) {

            logger.warn("Email already exists: {}",
                    employeeRequest.getEmail());

            throw new BadRequestException(
                    "Email already exists: " + employeeRequest.getEmail());
        }

        // Convert Request DTO to Entity
        Employee employee = EmployeeMapper.toEntity(employeeRequest);

        // Save Employee
        Employee savedEmployee = employeeRepository.save(employee);

        logger.info("Employee created successfully with Database ID: {}",
                savedEmployee.getId());

        // Convert Entity to Response DTO
        return EmployeeMapper.toResponse(savedEmployee);
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {

        logger.info("Fetching all employees.");

        List<Employee> employees = employeeRepository.findAll();

        logger.info("Total employees found: {}", employees.size());

        return employees.stream()
                .map(EmployeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {

        logger.info("Fetching employee with ID: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {

                    logger.error("Employee not found with ID: {}", id);

                    return new ResourceNotFoundException(
                            "Employee not found with ID: " + id);
                });

        logger.info("Employee fetched successfully with ID: {}", id);

        return EmployeeMapper.toResponse(employee);
    }



    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest employeeRequest) {

        logger.info("Updating employee with ID: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {

                    logger.error("Employee not found with ID: {}", id);

                    return new ResourceNotFoundException(
                            "Employee not found with ID: " + id);
                });

        if (!employee.getEmployeeId().equals(employeeRequest.getEmployeeId())
                && employeeRepository.existsByEmployeeId(employeeRequest.getEmployeeId())) {

            logger.warn("Employee ID already exists: {}",
                    employeeRequest.getEmployeeId());

            throw new BadRequestException(
                    "Employee ID already exists: " + employeeRequest.getEmployeeId());
        }

        if (!employee.getEmail().equals(employeeRequest.getEmail())
                && employeeRepository.existsByEmail(employeeRequest.getEmail())) {

            logger.warn("Email already exists: {}",
                    employeeRequest.getEmail());

            throw new BadRequestException(
                    "Email already exists: " + employeeRequest.getEmail());
        }

        EmployeeMapper.updateEntity(employee, employeeRequest);

        Employee updatedEmployee = employeeRepository.save(employee);

        logger.info("Employee updated successfully with ID: {}",
                updatedEmployee.getId());

        return EmployeeMapper.toResponse(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {

        logger.info("Deleting employee with ID: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {

                    logger.error("Employee not found with ID: {}", id);

                    return new ResourceNotFoundException(
                            "Employee not found with ID: " + id);
                });

        employeeRepository.delete(employee);

        logger.info("Employee deleted successfully with ID: {}", id);
    }
}