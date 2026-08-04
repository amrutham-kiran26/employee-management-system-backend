package com.kiran.ems.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private String role;

    private String token;

    private String message;


}