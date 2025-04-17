package com.task.demo.controller;

import com.task.demo.DTOs.AccountTransferDTO;
import com.task.demo.DTOs.request.AccountInsertDTO;
import com.task.demo.DTOs.request.AccountUpdateDTO;
import com.task.demo.DTOs.response.AccountDTO;
import com.task.demo.services.AccountServcie;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/accounts")
public class AccountController {

    @Autowired
    private AccountServcie accountServcie;
    @PostMapping(path = "/save-account")
    public AccountDTO saveAccount(@RequestBody @Valid AccountInsertDTO accountInsertDTO) {
        return accountServcie.saveAccount(accountInsertDTO);
    }
    @PutMapping("/update/{id}")
    public AccountDTO updatedAccount(@PathVariable(name = "id") Long id, @RequestBody AccountUpdateDTO accountUpdateDTO) {
        return accountServcie.updatedAccount(id, accountUpdateDTO);
    }
    @GetMapping(path = "/get-account/{id}")
    public AccountDTO getAccountById(@PathVariable(name = "id") Long id) {
        return accountServcie.getAccountById(id);
    }
    @GetMapping(path = "/get-all-accounts")
    public List<AccountDTO> getAllAccount() {
        return accountServcie.getAllAccount();
    }
    @GetMapping(path = "/get-user-account/{id}")
    public List<AccountDTO> getAccountByUserId(@PathVariable(name="id") Long userId) {
        return accountServcie.getAccountByUserId(userId);
    }

    @PutMapping("/transfer-money")
    public String transferMoney(@RequestBody @Valid AccountTransferDTO accountTransferDTO) {
        return accountServcie.transferMoney(accountTransferDTO);
    }
}
