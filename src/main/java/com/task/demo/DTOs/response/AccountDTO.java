package com.task.demo.DTOs.response;

import com.task.demo.enums.CurrencyType;
import com.task.demo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private Long id;
    private String accountNumber;
    private String iban;
    private CurrencyType currencyType;
    private LocalDate expiredDate;
    private Status status;
    private BigDecimal balance;
    private Long userId;
    private Long branchId;
}
