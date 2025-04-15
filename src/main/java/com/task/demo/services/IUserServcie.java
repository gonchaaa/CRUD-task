package com.task.demo.services;

import com.task.demo.DTOs.response.UserDTO;
import com.task.demo.DTOs.filter.UserFilterDTO;
import com.task.demo.DTOs.request.UserUIDTO;
import com.task.demo.entities.User;

import java.util.List;

public interface IUserServcie {
     UserDTO saveUser(UserUIDTO userUIDTO);
     List<UserDTO> getAllUsers();
     UserDTO getUserById(Integer id);
     List<User> getUserFilter(UserFilterDTO userFilterDTO);
     String deleteUserById(Integer id);
     UserDTO updateUser (Integer id,UserUIDTO userUIDTO);
}
