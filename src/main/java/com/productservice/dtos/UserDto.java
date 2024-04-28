package com.productservice.dtos;

import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    private boolean isEmailVerified;

    @ManyToMany
    private List<RoleDto> roles;
}