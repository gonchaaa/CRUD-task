package com.task.demo.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
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
    @Min(value = 18, message = "yasdan 18-den yuxari olmalidir")
    private Integer age;
    @Email(message = "email duzgun deyil")
    private String email;
    private Date birthDate;
}
