package com.task.demo.DTOs.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUIDTO {

    @NotBlank(message = "firstName bos ola bilmez")
    private String firstName;
    @NotBlank(message = "lastName bos ola bilmez")
    private String lastName;
    @Min(value = 18, message = "yas 18-den yuxari olmalidir")
    private Integer age;
    @Email(message = "email duzgun deyil")
    private String email;
    @NotBlank(message = "password bos ola bilmez")
    private String password;
    private Date birthDate;
}
