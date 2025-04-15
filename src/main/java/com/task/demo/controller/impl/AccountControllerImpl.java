package com.task.demo.controller.impl;

import com.task.demo.DTOs.AccountTransferDTO;
import com.task.demo.DTOs.request.AccountInsertDTO;
import com.task.demo.DTOs.request.AccountUpdateDTO;
import com.task.demo.DTOs.response.AccountDTO;
import com.task.demo.controller.IAccountController;
import com.task.demo.services.IAccountServcie;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/accounts")
public class AccountControllerImpl implements IAccountController {

    @Autowired
    private IAccountServcie accountServcie;
    @PostMapping(path = "/save-account")
    @Override
    public AccountDTO saveAccount(@RequestBody @Valid AccountInsertDTO accountInsertDTO) {
        return accountServcie.saveAccount(accountInsertDTO);
    }
    @PutMapping("/update/{id}")
    @Override
    public AccountDTO updatedAccount(@PathVariable(name = "id") Long id, @RequestBody AccountUpdateDTO accountUpdateDTO) {
        return accountServcie.updatedAccount(id, accountUpdateDTO);
    }
    @GetMapping(path = "/get-account/{id}")
    @Override
    public AccountDTO getAccountById(@PathVariable(name = "id") Long id) {
        return accountServcie.getAccountById(id);
    }
    @GetMapping(path = "/get-all-accounts")
    @Override
    public List<AccountDTO> getAllAccount() {
        return accountServcie.getAllAccount();
    }
    @GetMapping(path = "/get-user-account/{id}")
    @Override
    public List<AccountDTO> getAccountByUserId(@PathVariable(name="id") Long userId) {
        return accountServcie.getAccountByUserId(userId);
    }

    @PutMapping("/transfer-money")
    @Override
    public String transferMoney(@RequestBody @Valid AccountTransferDTO accountTransferDTO) {
        return accountServcie.transferMoney(accountTransferDTO);
    }
}
