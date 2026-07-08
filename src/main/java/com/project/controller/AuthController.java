package com.project.controller;


import com.project.dto.ApiResponse;
import com.project.dto.request.LoginRequest;
import com.project.dto.response.LoginResponse;
import com.project.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final LoginService loginService;

    AuthController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = loginService.login(loginRequest);
        return new ApiResponse<>(true, loginResponse);
    }

}
