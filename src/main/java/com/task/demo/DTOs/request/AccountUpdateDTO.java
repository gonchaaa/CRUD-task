package com.task.demo.DTOs.request;

import com.task.demo.enums.CurrencyType;
import com.task.demo.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountUpdateDTO {
    @NotBlank(message = "iban bos ola bilmez")
    private String iban;
    private CurrencyType currencyType;
    private LocalDate expiredDate;
    private Status status;
    @NotNull(message = "balans istifadeci is-si bos ola bilmez")
    private BigDecimal balance;
}
