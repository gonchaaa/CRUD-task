package com.task.demo.services.impl;

import com.task.demo.DTOs.AccountTransferDTO;
import com.task.demo.DTOs.request.AccountInsertDTO;
import com.task.demo.DTOs.request.AccountUpdateDTO;
import com.task.demo.DTOs.response.AccountDTO;
import com.task.demo.entities.Account;
import com.task.demo.entities.Branch;
import com.task.demo.entities.User;
import com.task.demo.enums.CurrencyType;
import com.task.demo.enums.Status;
import com.task.demo.exceptions.BaseException;
import com.task.demo.exceptions.ErrorMessage;
import com.task.demo.exceptions.ErrorsType;
import com.task.demo.repository.AccountRepository;
import com.task.demo.repository.BranchRepository;
import com.task.demo.repository.UserRepository;
import com.task.demo.services.AccountServcie;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.task.demo.generator.AccountGeneretor.generatorAccountNumber;

@Service
public class AccountServcieImpl implements AccountServcie {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Override
    public AccountDTO saveAccount(@Valid AccountInsertDTO accountInsertDTO) {
        User user = userRepository.findById(Math.toIntExact(accountInsertDTO.getUserId())).orElseThrow(() -> new EntityNotFoundException("Bele bir istifadeci yoxdur"));
        Branch branch = branchRepository.findById(accountInsertDTO.getBranchId()).orElseThrow(() -> new EntityNotFoundException("Bele bir filial yoxdur"));



            Account account = new Account();

            BeanUtils.copyProperties(accountInsertDTO, account);

            account.setUser(user);
            account.setBranch(branch);
            account.setAccountNumber(generatorAccountNumber());
            account.setStatus(Status.PENDING);
            account.setBalance(BigDecimal.ZERO);
            account.setCurrencyType(CurrencyType.AZN);
            account.setExpiredDate(LocalDate.now().plusYears(5));
            try {
                Account dbAccount = accountRepository.save(account);


                AccountDTO accountDTO = new AccountDTO();
                BeanUtils.copyProperties(dbAccount, accountDTO);
                accountDTO.setUserId(user.getId());
                accountDTO.setBranchId(branch.getId());

                return accountDTO;
            }catch (DataIntegrityViolationException e) {
                throw new BaseException(new ErrorMessage(ErrorsType.UNIQUE_CONSTRAINT,
                        account.getIban()));
            }
            catch ( EntityNotFoundException e) {
                throw new BaseException(new ErrorMessage(ErrorsType.INVALID_DATA, account.getIban()));
            }




    }

    @Override
    public AccountDTO updatedAccount(Long id, AccountUpdateDTO accountUpdateDTO) {
        AccountDTO accountDTO = new AccountDTO();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Optional<Account> accountOptional = accountRepository.findById(id);
        if(accountOptional.isPresent()){
            Account account = accountOptional.get();

         account.setIban(accountUpdateDTO.getIban());
         account.setBalance(accountUpdateDTO.getBalance());
         account.setCurrencyType(accountUpdateDTO.getCurrencyType());
         account.setExpiredDate(accountUpdateDTO.getExpiredDate());
            if ("ADMIN".equals(role)) {
                account.setStatus(accountUpdateDTO.getStatus());
            }

         accountDTO.setUserId(account.getUser().getId());
         accountDTO.setBranchId(account.getBranch().getId());

            Account updatedAccount = accountRepository.save(account);
            BeanUtils.copyProperties(updatedAccount,accountDTO);
            return accountDTO;

        }
        throw new BaseException(new ErrorMessage(ErrorsType.NO_DATA_FOUND,id.toString()));
    }

    @Override
    public AccountDTO getAccountById(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if(accountOptional.isPresent()){
            AccountDTO accountDTO = new AccountDTO();
            Account account = accountOptional.get();
            BeanUtils.copyProperties(account,accountDTO);
            accountDTO.setUserId(account.getUser().getId());
            accountDTO.setBranchId(account.getBranch().getId());
            return accountDTO;
        }
        throw new BaseException(new ErrorMessage(ErrorsType.NO_DATA_FOUND,id.toString()));
    }

    @Override
    public List<AccountDTO> getAllAccount() {
        List<AccountDTO> accountDTOList = new ArrayList<>();
        List<Account> accounts = accountRepository.findAll();
        for (Account account : accounts) {
            AccountDTO accountDTO = new AccountDTO();
            BeanUtils.copyProperties(account,accountDTO);
            accountDTO.setUserId(account.getUser().getId());
            accountDTO.setBranchId(account.getBranch().getId());
            accountDTOList.add(accountDTO);
        }
        return accountDTOList;
    }

    @Override
    public List<AccountDTO> getAccountByUserId(Long userId) {
        User user = userRepository.findById(Math.toIntExact(userId)).orElseThrow(() -> new EntityNotFoundException("Bele bir istifadeci yoxdur"));
        List<AccountDTO> accountDTOList = new ArrayList<>();
        List<Account> accounts = accountRepository.findAllByUser(user);
        for (Account account : accounts) {
            AccountDTO accountDTO = new AccountDTO();
            BeanUtils.copyProperties(account,accountDTO);
            accountDTO.setUserId(account.getUser().getId());
            accountDTO.setBranchId(account.getBranch().getId());
            accountDTOList.add(accountDTO);
        }
        return accountDTOList;
    }


    @Transactional
    @Override
    public String transferMoney(AccountTransferDTO accountTransferDTO) {


        Account fromAccount = accountRepository.findById(accountTransferDTO.getFromAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Gonderici hesab tapilmadi"));
        Account toAccount = accountRepository.findById(accountTransferDTO.getToAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Qebul eden hesab tapilmadi"));

        if (fromAccount.getStatus() == Status.PENDING || fromAccount.getStatus() == Status.BLOCKED) {
            throw new BaseException(new ErrorMessage(ErrorsType.INVALID_OPERATION, fromAccount.getIban().toString()));
        }
        if (toAccount.getStatus() == Status.PENDING || toAccount.getStatus() == Status.BLOCKED) {
            throw new BaseException(new ErrorMessage(ErrorsType.INVALID_OPERATION, toAccount.getIban().toString()));
        }

        if(fromAccount.getBalance().compareTo(BigDecimal.ZERO) == 0 ||
                fromAccount.getBalance().compareTo(accountTransferDTO.getAmount()) < 0 ) {
           throw new BaseException(new ErrorMessage(ErrorsType.NO_BALANCE, fromAccount.getIban().toString()));
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(accountTransferDTO.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(accountTransferDTO.getAmount()));
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        return "Success transfer";
    }
}
