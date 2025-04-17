package com.task.demo.DTOs.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountInsertDTO {
    @NotBlank(message = "IBAN boş ola bilməz")
    private String iban;
    @NotNull(message = "istifadeci id-si bos ola bilmez")
    private Long userId;
    @NotNull(message = "filial id-si bos ola bilmez")
    private Long branchId;
}
