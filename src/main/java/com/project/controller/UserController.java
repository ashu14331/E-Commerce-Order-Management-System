package com.project.controller;


import com.project.dto.ApiResponse;
import com.project.dto.request.UserRequest;
import com.project.dto.response.UserResponse;
import com.project.service.EmailService;
import com.project.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController{

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;


    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@Valid @RequestBody UserRequest userRequest){
        log.info("Star::register in UserController api  {}", userRequest);
        UserResponse response = userService.register(userRequest);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>(true, "User registered successfully",response);
        emailService.sendRegistrationEmail(userRequest.getEmail());
        log.info("End::register in UserController api  {}", apiResponse);
        return apiResponse;
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> fetch(@PathVariable Long id) {
        log.info("Star::fetching in UserController api  {}", id);
        UserResponse response = userService.fetch(id);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>(true,response);
        log.info("End::fetching is completed  {}", apiResponse);
        return apiResponse;
    }

    @PutMapping("/update/{id}")
    public ApiResponse<String> update(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        String message = userService.update(id, userRequest);
        return new ApiResponse<>(true, message);
    }

    @GetMapping("/lists")
    public Page<UserResponse> lists(@RequestParam int page, @RequestParam int size) {
        log.info("Star::lists in UserController api ");
        Page<UserResponse> userResponsePage =   userService.list(page,size);
        log.info("End::lists in UserController api ");
        return userResponsePage;
    }

    @DeleteMapping("/{id}")
    public void deleted(@PathVariable Long id) {
        userService.delete(id);
    }
}
