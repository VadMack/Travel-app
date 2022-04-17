package com.vas.travelapp.api.dtos;


import com.vas.travelapp.domain.user.Role;
import lombok.Data;

import java.util.Set;


@Data
public class UserDto {
    private String id;
    private String username;
    private Set<Role> authorities;
}
