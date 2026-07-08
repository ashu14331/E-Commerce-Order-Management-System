package com.project.service;

import com.project.dto.request.RoleRequest;
import com.project.dto.response.RoleResponse;

public interface RoleService {

    public RoleResponse createRole(RoleRequest roleRequest);
}
