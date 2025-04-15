package com.task.demo.DTOs.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountInsertDTO {
    private String iban;
    private Long userId;
    private Long branchId;
}
