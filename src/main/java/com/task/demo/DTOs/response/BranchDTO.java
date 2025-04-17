package com.task.demo.DTOs.response;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchDTO {
    private Long id;
    private String branchName;
    private String branchAddress;
    private Integer branchCode;
}
