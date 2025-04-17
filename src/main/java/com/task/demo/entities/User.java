package com.task.demo.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.task.demo.enums.CurrencyType;
import com.task.demo.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "userlist",uniqueConstraints = @UniqueConstraint(columnNames = "first_name"))
public class User {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name",nullable = false,unique = true)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "age",nullable = false)
    private Integer age;

    @Email(message = "email duzgun deyil")
    @Column(name = "email",nullable = false)
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_Date",nullable = true)
    private Date birthDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false)

    private Role role;

}
