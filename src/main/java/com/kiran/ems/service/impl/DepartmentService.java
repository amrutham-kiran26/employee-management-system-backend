package com.kiran.ems.service;

import com.kiran.ems.dto.DepartmentRequest;
import com.kiran.ems.dto.DepartmentResponse;

import java.util.List;

public interface DepartmentService {

    DepartmentResponse createDepartment(DepartmentRequest departmentRequest);

    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse getDepartmentById(Long id);

    DepartmentResponse updateDepartment(Long id, DepartmentRequest departmentRequest);

    void deleteDepartment(Long id);

}