package com.project.service.serviceImpl;

import com.project.dto.request.LoginRequest;
import com.project.dto.response.LoginResponse;
import com.project.entity.Role;
import com.project.entity.User;
import com.project.repository.UserRepository;
import com.project.service.LoginService;
import com.project.util.JwtUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder,JwtUtil jwtUtil){
        this .userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest){

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        User user = userRepository.findByEmailAndIsDeletedFalse(email).orElseThrow(() -> new BadCredentialsException("User Not Found"));
        if(user.isDeleted()){
            throw new BadCredentialsException("User Is Deleted");
        }
        Set<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong Password");
        }
        String token = jwtUtil.generateToken(email, roles);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(token);
        loginResponse.setTokenType("Bearer");
        loginResponse.setRole(roles);
        return loginResponse;
    }
}
