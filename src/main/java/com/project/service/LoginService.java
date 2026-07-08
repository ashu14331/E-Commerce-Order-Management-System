package com.project.service;

import com.project.dto.request.LoginRequest;
import com.project.dto.response.LoginResponse;

public interface LoginService{

     LoginResponse login(LoginRequest loginRequest);
}
