package com.task.demo.controller.impl;

import com.task.demo.DTOs.UserDTO;
import com.task.demo.DTOs.UserFilterDTO;
import com.task.demo.DTOs.UserUIDTO;
import com.task.demo.controller.IUserController;
import com.task.demo.entities.User;
import com.task.demo.services.IUserServcie;
import jakarta.validation.Valid;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/rest/api/user")
public class UserControllerImpl implements IUserController {

    @Autowired
    private IUserServcie userServcie;


    @PostMapping(path = "/save")
    @Override
    public UserDTO saveUser(@RequestBody @Valid UserUIDTO userUIDTO) {
        return userServcie.saveUser(userUIDTO);
    }

    @GetMapping(path = "/get-all-users")
    @Override
    public List<UserDTO> getAllUsers() {
        return userServcie.getAllUsers();
    }

    @GetMapping(path = "/get-user/{id}")
    @Override
    public UserDTO getUserById(@PathVariable(name = "id") Integer id) {
        return userServcie.getUserById(id);
    }

    @GetMapping(path = "/get-user-filter")
    @Override
    public List<User> getUserFilter(@RequestParam(required = false) String firstName,
                                    @RequestParam(required = false) String lastName,
                                    @RequestParam(required = false) Integer age
                                        ) {
        UserFilterDTO userFilterDTO = new UserFilterDTO();
        userFilterDTO.setFirstName(firstName);
        userFilterDTO.setLastName(lastName);
        userFilterDTO.setAge(age);

        return userServcie.getUserFilter(userFilterDTO);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public void deleteUserById(@PathVariable(name = "id") Integer id) {
        userServcie.deleteUserById(id);
    }
    @PutMapping("/update/{id}")
    @Override
    public UserDTO updateUser(@PathVariable(name = "id") Integer id,@RequestBody UserUIDTO userUIDTO) {
    return userServcie.updateUser(id, userUIDTO);
    }
}
