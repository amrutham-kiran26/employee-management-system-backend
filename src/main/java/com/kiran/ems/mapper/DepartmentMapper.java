package com.kiran.ems.mapper;

import com.kiran.ems.dto.DepartmentRequest;
import com.kiran.ems.dto.DepartmentResponse;
import com.kiran.ems.entity.Department;

public class DepartmentMapper {

    public static Department toEntity(DepartmentRequest request) {

        return Department.builder()
                .departmentCode(request.getDepartmentCode())
                .departmentName(request.getDepartmentName())
                .description(request.getDescription())
                .status(request.getStatus())
                .build();
    }

    public static DepartmentResponse toResponse(Department department) {

        return DepartmentResponse.builder()
                .id(department.getId())
                .departmentCode(department.getDepartmentCode())
                .departmentName(department.getDepartmentName())
                .description(department.getDescription())
                .status(department.getStatus())
                .createdAt(department.getCreatedAt())
                .updatedAt(department.getUpdatedAt())
                .build();
    }

    public static void updateEntity(Department department,
                                    DepartmentRequest request) {

        department.setDepartmentCode(request.getDepartmentCode());
        department.setDepartmentName(request.getDepartmentName());
        department.setDescription(request.getDescription());
        department.setStatus(request.getStatus());
    }
}