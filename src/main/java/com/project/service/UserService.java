package com.project.service;

import com.project.dto.request.UserRequest;
import com.project.dto.response.UserResponse;
import org.springframework.data.domain.Page;

public interface UserService{

     UserResponse register(UserRequest userRequest);

     UserResponse fetch(Long Id);

     Page<UserResponse> list(int page, int size);

     String  update (long id,UserRequest userRequest);

     void delete(long id);
     
}
