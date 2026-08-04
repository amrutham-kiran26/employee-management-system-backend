package com.kiran.ems.controller;

import com.kiran.ems.dto.EmployeeRequest;
import com.kiran.ems.dto.EmployeeResponse;
import com.kiran.ems.entity.Employee;
import com.kiran.ems.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)

    public EmployeeResponse createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.createEmployee(employeeRequest);
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','EMPLOYEE')")
    @GetMapping
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','EMPLOYEE')")
    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Long id){
        return employeeService.getEmployeeById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @PutMapping("/{id}")
    public EmployeeResponse updateEmployee(@Valid @RequestBody EmployeeRequest employeeRequest, @PathVariable Long id){
        return employeeService.updateEmployee(id,employeeRequest);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
    }


}




