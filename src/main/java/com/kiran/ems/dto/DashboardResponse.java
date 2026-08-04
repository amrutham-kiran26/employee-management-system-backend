package com.kiran.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

    private long totalEmployees;

    private long activeEmployees;

    private long inactiveEmployees;

    private long totalUsers;

    private long activeUsers;

    private long inactiveUsers;

}