package com.task.demo.controller;

import com.task.demo.DTOs.AccountTransferDTO;
import com.task.demo.DTOs.request.AccountInsertDTO;
import com.task.demo.DTOs.request.AccountUpdateDTO;
import com.task.demo.DTOs.response.AccountDTO;

import java.util.List;

public interface IAccountController {
    AccountDTO saveAccount(AccountInsertDTO accountInsertDTO);
    AccountDTO updatedAccount(Long id, AccountUpdateDTO accountUpdateDTO);
    AccountDTO getAccountById(Long id);
    List<AccountDTO> getAllAccount();
    List<AccountDTO> getAccountByUserId(Long userId);
    String transferMoney(AccountTransferDTO accountTransferDTO);
}
