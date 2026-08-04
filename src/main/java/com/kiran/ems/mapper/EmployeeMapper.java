package com.kiran.ems.mapper;

import com.kiran.ems.dto.EmployeeRequest;
import com.kiran.ems.dto.EmployeeResponse;
import com.kiran.ems.entity.Employee;

public class EmployeeMapper {

    // Convert Request DTO to Entity
    public static Employee toEntity(EmployeeRequest request) {

        return Employee.builder()
                .employeeId(request.getEmployeeId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .department(request.getDepartment())
                .designation(request.getDesignation())
                .salary(request.getSalary())
                .dateOfJoining(request.getDateOfJoining())
                .status(request.getStatus())
                .build();
    }

    // Convert Entity to Response DTO
    public static EmployeeResponse toResponse(Employee employee) {

        return EmployeeResponse.builder()
                .id(employee.getId())
                .employeeId(employee.getEmployeeId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .department(employee.getDepartment())
                .designation(employee.getDesignation())
                .salary(employee.getSalary())
                .dateOfJoining(employee.getDateOfJoining())
                .status(employee.getStatus())
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();
    }

    // Update existing Employee Entity
    public static void updateEntity(Employee employee, EmployeeRequest request) {

        employee.setEmployeeId(request.getEmployeeId());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setDepartment(request.getDepartment());
        employee.setDesignation(request.getDesignation());
        employee.setSalary(request.getSalary());
        employee.setDateOfJoining(request.getDateOfJoining());
        employee.setStatus(request.getStatus());
    }
}