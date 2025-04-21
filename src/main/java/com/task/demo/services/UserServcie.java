package com.task.demo.services;

import com.task.demo.DTOs.response.UserDTO;
import com.task.demo.DTOs.filter.UserFilterDTO;
import com.task.demo.DTOs.request.UserUIDTO;
import com.task.demo.entities.User;

import java.util.List;

public interface UserServcie {
     List<UserDTO> getAllUsers();
     UserDTO getUserById(Long id);
     List<User> getUserFilter(UserFilterDTO userFilterDTO);
     String deleteUserById(Long id);
     UserDTO updateUser (Long id,UserUIDTO userUIDTO);
}
