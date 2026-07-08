package com.project.dto.response;


import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class LoginResponse {
    private String accessToken;
    private String tokenType;
    private Set<String> role;

}
