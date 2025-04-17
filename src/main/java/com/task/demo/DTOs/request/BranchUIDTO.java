package com.task.demo.DTOs.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchUIDTO{

    @NotBlank(message = "Branch adı boş ola bilməz")
    private String branchName;
    @NotBlank(message = "Branch ünvanı boş ola bilməz")
    private String branchAddress;
    @NotNull(message = "Branch kodu boş ola bilməz")
    private Integer branchCode;
}
