package com.kiran.ems.service;

import com.kiran.ems.dto.LoginRequest;
import com.kiran.ems.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequest);

}