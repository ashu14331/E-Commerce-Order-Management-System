package com.project.service.serviceImpl;

import com.project.dto.request.UserRequest;
import com.project.dto.response.UserResponse;
import com.project.entity.Role;
import com.project.entity.User;
import com.project.exception.DuplicateResourceException;
import com.project.exception.ResourceNotFoundException;
import com.project.repository.RoleRepository;
import com.project.repository.UserRepository;
import com.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserResponse register(UserRequest userRequest) {

        log.info("Start ::Register service impl ");
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        for (String roles : userRequest.getRoles()) {
            if (!roleRepository.existsByName(roles)) {
                throw new DuplicateResourceException("Role does not exists");
            }
        }
        Set<Role> roles = roleRepository.findByNameIn(userRequest.getRoles());
        User user = prepareUserEntity(userRequest);
        user.setRoles(roles);
        User user1 = userRepository.save(user);
        log.info("End ::Register service impl ");
        return prepareUserResponse(user1);
    }


    @Override
    public String update(long id, UserRequest userRequest) {
        log.info("Start ::Update service impl ");
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("UserId not found"));
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setName(userRequest.getName());
        userRepository.save(user);
        log.info("End ::Update service impl ");
        return "Updated Successfully";
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserResponse fetch(Long Id) {
        log.info("Start ::Fetch service impl ");
        User user = userRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        log.info("End ::Fetch service impl ");
        return prepareUserResponse(user);
    }

    @Override
    public Page<UserResponse> list(int page, int size) {
        log.info("Start ::List service impl ");
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageRequest);
        List<UserResponse> dtolist = userPage.stream().map(this::prepareUserResponse).toList();
        log.info("End ::List service impl ");
        return new PageImpl<>(dtolist, pageRequest, userPage.getTotalElements());
    }

    public User prepareUserEntity(UserRequest userRequest) {
        log.info("Start::prepareUserEntity service impl ");
        User user = modelMapper.map(userRequest, User.class);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        log.info("End::prepareUserEntity service impl ");
        return user;
    }

    public UserResponse prepareUserResponse(User user1) {
        log.info("Start::prepareUserResponse service impl ");
        UserResponse userResponse = modelMapper.map(user1, UserResponse.class);
        userResponse.setRoles(user1.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
        log.info("End::prepareUserResponse service impl ");
        return userResponse;
    }
}
