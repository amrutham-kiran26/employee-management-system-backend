package com.kiran.ems.repository;

import com.kiran.ems.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    boolean existsByDepartmentCode(String departmentCode);

    boolean existsByDepartmentName(String departmentName);

}