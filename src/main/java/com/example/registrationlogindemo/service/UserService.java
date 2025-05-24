package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
    
    public User getUserById(Long id);
    
    public void updateUser(Long id, UserDto userDto);
    public void deleteUserById(Long id);
    
    
    public List<UserDto> findAllUsersNotDeleted();
}
