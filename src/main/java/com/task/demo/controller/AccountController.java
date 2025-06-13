package com.task.demo.controller;

import com.task.demo.DTOs.AccountTransferDTO;
import com.task.demo.DTOs.request.AccountInsertDTO;
import com.task.demo.DTOs.request.AccountUpdateDTO;
import com.task.demo.DTOs.response.AccountDTO;
import com.task.demo.services.AccountServcie;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountServcie accountServcie;

    @PostMapping()
    public AccountDTO saveAccount(@RequestBody @Valid AccountInsertDTO accountInsertDTO) {
        log.info("Account creation request received: {}", accountInsertDTO);
        return accountServcie.saveAccount(accountInsertDTO);
    }

    @PutMapping("/update/{id}")
    public AccountDTO updatedAccount(@PathVariable(name = "id") Long id, @RequestBody AccountUpdateDTO accountUpdateDTO) {
        log.info("Account update request received for ID {}: {}", id, accountUpdateDTO);
        return accountServcie.updatedAccount(id, accountUpdateDTO);
    }

    @GetMapping(path = "/get-account/{id}")
    public AccountDTO getAccountById(@PathVariable(name = "id") Long id) {
        log.info("Fetching account with ID: {}", id);
        return accountServcie.getAccountById(id);
    }

    @GetMapping(path = "/get-all-accounts")
    public List<AccountDTO> getAllAccount() {
        log.info("Fetching all accounts");

        return accountServcie.getAllAccount();
    }


    @GetMapping(path = "/get-user-account/{id}")
    public List<AccountDTO> getAccountByUserId(@PathVariable(name="id") Long userId) {
        log.info("Fetching accounts for user ID: {}", userId);
        return accountServcie.getAccountByUserId(userId);
    }

    @PutMapping("/transfer-money")
    public String transferMoney(@RequestBody @Valid AccountTransferDTO accountTransferDTO) {
        log.info("Money transfer request received: {}", accountTransferDTO);
        return accountServcie.transferMoney(accountTransferDTO);
    }
}
