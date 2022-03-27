package com.vas.travelapp.domain.dto;


import com.vas.travelapp.domain.entity.Role;
import lombok.Data;

import java.util.Set;


@Data
public class UserDto {
    private String id;
    private String username;
    private Set<Role> authorities;
}
