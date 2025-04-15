package com.task.demo.DTOs.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchUIDTO{

    private String branchName;

    private String branchAddress;

    private Integer branchCode;
}
