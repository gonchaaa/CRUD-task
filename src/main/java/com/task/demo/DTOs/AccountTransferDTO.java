package com.task.demo.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransferDTO {
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;
}
