package com.task.demo.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "branches")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String branchName;
    @Column(nullable = false)
    private String branchAddress;
    @Column(nullable = false)
    private Integer branchCode;
    @ManyToMany(mappedBy = "branches")
    private List<User> user =  new ArrayList<>();
}
