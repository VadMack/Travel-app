package com.vadmack.core.domain.dto;


import com.vadmack.core.domain.entity.Role;
import lombok.Data;

import java.util.Set;


@Data
public class UserDto {
    private String id;
    private String username;
    private Set<Role> authorities;
}
