package com.task.demo.services.impl;

import com.task.demo.DTOs.response.UserDTO;
import com.task.demo.DTOs.filter.UserFilterDTO;
import com.task.demo.DTOs.request.UserUIDTO;
import com.task.demo.entities.User;
import com.task.demo.enums.Role;
import com.task.demo.exceptions.BaseException;
import com.task.demo.exceptions.ErrorMessage;
import com.task.demo.exceptions.ErrorsType;
import com.task.demo.repository.UserRepository;
import com.task.demo.services.UserServcie;
import com.task.demo.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServcie {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOList = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            modelMapper.map(user,userDTO);

            userDTOList.add(userDTO);
        }
    return userDTOList;


    }

    @Override
    public UserDTO getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            UserDTO userDTO = new UserDTO();
            User user = userOptional.get();
            modelMapper.map(user,userDTO);

            return userDTO;
        }
        throw new BaseException(new ErrorMessage(ErrorsType.NO_DATA_FOUND,id.toString()));
    }

    @Override
    public List<User> getUserFilter(UserFilterDTO userFilterDTO) {
        UserSpecification userSpecification = new UserSpecification(userFilterDTO);
        return userRepository.findAll(userSpecification);
    }

    @Override
    public String deleteUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            userRepository.deleteById(id);
            return  "success";
        }
        throw new BaseException(new ErrorMessage(ErrorsType.NO_DATA_FOUND,id.toString()));

    }

    @Override
    public UserDTO updateUser(Long id, UserUIDTO userUIDTO) {
        UserDTO userDTO = new UserDTO();
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
           User user = userOptional.get();

           user.setEmail(userUIDTO.getEmail());
           user.setFirstName(userUIDTO.getFirstName());
           user.setLastName(userUIDTO.getLastName());
           user.setBirthDate(userUIDTO.getBirthDate());
           user.setAge(userUIDTO.getAge());

            User updatedUser = userRepository.save(user);
            modelMapper.map(updatedUser,userDTO);

            return userDTO;

        }
        throw new BaseException(new ErrorMessage(ErrorsType.NO_DATA_FOUND,id.toString()));

    }
}
