package com.kiran.ems.service.impl;

import com.kiran.ems.dto.DepartmentRequest;
import com.kiran.ems.dto.DepartmentResponse;
import com.kiran.ems.entity.Department;
import com.kiran.ems.exception.BadRequestException;
import com.kiran.ems.exception.ResourceNotFoundException;
import com.kiran.ems.mapper.DepartmentMapper;
import com.kiran.ems.repository.DepartmentRepository;
import com.kiran.ems.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger logger =
            LoggerFactory.getLogger(DepartmentServiceImpl.class);

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest departmentRequest) {

        logger.info("Creating department with code: {}", departmentRequest.getDepartmentCode());

        if (departmentRepository.existsByDepartmentCode(departmentRequest.getDepartmentCode())) {

            logger.warn("Department code already exists: {}", departmentRequest.getDepartmentCode());

            throw new BadRequestException(
                    "Department code already exists: " + departmentRequest.getDepartmentCode());
        }

        if (departmentRepository.existsByDepartmentName(departmentRequest.getDepartmentName())) {

            logger.warn("Department name already exists: {}", departmentRequest.getDepartmentName());

            throw new BadRequestException(
                    "Department name already exists: " + departmentRequest.getDepartmentName());
        }

        Department department = DepartmentMapper.toEntity(departmentRequest);

        Department savedDepartment = departmentRepository.save(department);

        logger.info("Department created successfully with ID: {}", savedDepartment.getId());

        return DepartmentMapper.toResponse(savedDepartment);
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {

        logger.info("Fetching all departments.");

        List<Department> departments = departmentRepository.findAll();

        logger.info("Total departments found: {}", departments.size());

        return departments.stream()
                .map(DepartmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {

        logger.info("Fetching department with ID: {}", id);

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> {

                    logger.error("Department not found with ID: {}", id);

                    return new ResourceNotFoundException(
                            "Department not found with ID: " + id);
                });

        return DepartmentMapper.toResponse(department);
    }

    @Override
    public DepartmentResponse updateDepartment(Long id,
                                               DepartmentRequest departmentRequest) {

        logger.info("Updating department with ID: {}", id);

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> {

                    logger.error("Department not found with ID: {}", id);

                    return new ResourceNotFoundException(
                            "Department not found with ID: " + id);
                });

        if (!department.getDepartmentCode().equals(departmentRequest.getDepartmentCode())
                && departmentRepository.existsByDepartmentCode(departmentRequest.getDepartmentCode())) {

            logger.warn("Department code already exists: {}", departmentRequest.getDepartmentCode());

            throw new BadRequestException(
                    "Department code already exists: " + departmentRequest.getDepartmentCode());
        }

        if (!department.getDepartmentName().equals(departmentRequest.getDepartmentName())
                && departmentRepository.existsByDepartmentName(departmentRequest.getDepartmentName())) {

            logger.warn("Department name already exists: {}", departmentRequest.getDepartmentName());

            throw new BadRequestException(
                    "Department name already exists: " + departmentRequest.getDepartmentName());
        }

        DepartmentMapper.updateEntity(department, departmentRequest);

        Department updatedDepartment = departmentRepository.save(department);

        logger.info("Department updated successfully with ID: {}", updatedDepartment.getId());

        return DepartmentMapper.toResponse(updatedDepartment);
    }

    @Override
    public void deleteDepartment(Long id) {

        logger.info("Deleting department with ID: {}", id);

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> {

                    logger.error("Department not found with ID: {}", id);

                    return new ResourceNotFoundException(
                            "Department not found with ID: " + id);
                });

        departmentRepository.delete(department);

        logger.info("Department deleted successfully with ID: {}", id);
    }
}