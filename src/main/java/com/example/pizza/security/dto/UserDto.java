package com.example.pizza.security.dto;

import com.example.pizza.security.model.Role;
import com.example.pizza.security.model.Status;
import lombok.Data;

@Data
public class UserDto {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
    private Status status;
}
