package com.task.demo.services;

import com.task.demo.DTOs.UserDTO;
import com.task.demo.DTOs.UserFilterDTO;
import com.task.demo.DTOs.UserUIDTO;
import com.task.demo.entities.User;

import java.util.List;

public interface IUserServcie {
    public UserDTO saveUser(UserUIDTO userUIDTO);
    public List<UserDTO> getAllUsers();
    public UserDTO getUserById(Integer id);
    public List<User> getUserFilter(UserFilterDTO userFilterDTO);
    public void deleteUserById(Integer id);
    public UserDTO updateUser (Integer id,UserUIDTO userUIDTO);
}
