package com.kiran.ems.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class EmployeeResponse {

    private Long id;
    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String department;
    private String designation;
    private BigDecimal salary;
    private LocalDate dateOfJoining;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}