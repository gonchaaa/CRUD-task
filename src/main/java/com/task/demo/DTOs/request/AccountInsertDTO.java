package com.task.demo.DTOs.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountInsertDTO {
    @NotBlank(message = "IBAN boş ola bilməz")
    @Pattern(regexp = "[A-Z]{2}\\d{2}[A-Z0-9]{1,30}", message = "IBAN-i duzgun daxil edin")
    private String iban;
    @NotNull(message = "istifadeci id-si bos ola bilmez")
    private Long userId;
    @NotNull(message = "filial id-si bos ola bilmez")
    private Long branchId;
}
