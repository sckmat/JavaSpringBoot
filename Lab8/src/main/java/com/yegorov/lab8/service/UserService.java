package com.yegorov.lab8.service;

import com.yegorov.lab8.dto.UserDto;
import com.yegorov.lab8.entity.User;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
