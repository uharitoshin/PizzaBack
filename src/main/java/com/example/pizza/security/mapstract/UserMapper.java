package com.example.pizza.security.mapstract;

import com.example.pizza.security.dto.UserDto;
import com.example.pizza.security.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User fromDto(UserDto userDto);
}
