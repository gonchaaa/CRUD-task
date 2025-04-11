package com.task.demo.controller;

import com.task.demo.DTOs.UserDTO;
import com.task.demo.DTOs.UserFilterDTO;
import com.task.demo.DTOs.UserUIDTO;
import com.task.demo.entities.User;

import java.util.List;

public interface IUserController {
    public UserDTO saveUser(UserUIDTO userUIDTO);
    public List<UserDTO> getAllUsers();
    public UserDTO getUserById(Integer id);
    public List<User> getUserFilter(String firstName, String lastName, Integer age);
    public void deleteUserById(Integer id);
    public UserDTO updateUser (Integer id,UserUIDTO userUIDTO);
}
