package com.task.demo.DTOs.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchFilterDTO {
    private String branchName;

    private String branchAddress;

    private Integer branchCode;
}
