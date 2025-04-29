package com.task.demo.controller;

import com.task.demo.DTOs.response.UserDTO;
import com.task.demo.DTOs.filter.UserFilterDTO;
import com.task.demo.DTOs.request.UserUIDTO;
import com.task.demo.entities.User;
import com.task.demo.services.UserServcie;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServcie userServcie;

    @GetMapping(path = "/get-all-users")
    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        return userServcie.getAllUsers();
    }

    @GetMapping(path = "/get-user/{id}")
    public UserDTO getUserById(@PathVariable(name = "id") Long id) {

        log.info("Fetching user by id: {}", id);
        return userServcie.getUserById(id);
    }

    @GetMapping(path = "/get-user-filter")
    public List<User> getUserFilter(@RequestParam(required = false) String firstName,
                                    @RequestParam(required = false) String lastName,
                                    @RequestParam(required = false) Integer age
                                    ){

        log.info("Fetching users with filter: firstName={}, lastName={}, age={}", firstName, lastName, age);
        UserFilterDTO userFilterDTO = new UserFilterDTO();
        userFilterDTO.setFirstName(firstName);
        userFilterDTO.setLastName(lastName);
        userFilterDTO.setAge(age);

        return userServcie.getUserFilter(userFilterDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable(name = "id") Long id) {
        log.info("Deleting user by id: {}", id);
       return userServcie.deleteUserById(id);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable(name = "id") Long id,@RequestBody UserUIDTO userUIDTO) {
        log.info("Updating user with id: {}", id);
    return userServcie.updateUser(id, userUIDTO);
    }
}
