package com.task.demo.controller;

import com.task.demo.DTOs.response.UserDTO;
import com.task.demo.DTOs.request.UserUIDTO;
import com.task.demo.entities.User;

import java.util.List;

public interface IUserController {
     UserDTO saveUser(UserUIDTO userUIDTO);
     List<UserDTO> getAllUsers();
     UserDTO getUserById(Integer id);
     List<User> getUserFilter(String firstName, String lastName, Integer age);
     String deleteUserById(Integer id);
     UserDTO updateUser (Integer id,UserUIDTO userUIDTO);
}
