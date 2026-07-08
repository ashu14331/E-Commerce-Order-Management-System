package com.project.dto.response;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Data
@Getter
@Setter
public class UserResponse {
    private long id;
    private String name;
    private String email;
    private Set<String> roles;

}
