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
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
@RequiredArgsConstructor
public class AccountServcieImpl implements AccountServcie {


    private final AccountRepository accountRepository;

    private final UserRepository userRepository;

    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;

    @Override
    public AccountDTO saveAccount(@Valid AccountInsertDTO accountInsertDTO) {
        User user = userRepository.findById(accountInsertDTO.getUserId()).orElseThrow(() -> new EntityNotFoundException("Bele bir istifadeci yoxdur"));
        Branch branch = branchRepository.findById(accountInsertDTO.getBranchId()).orElseThrow(() -> new EntityNotFoundException("Bele bir filial yoxdur"));
            Account account = new Account();
            account.setIban(accountInsertDTO.getIban());
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
                modelMapper.map(dbAccount, accountDTO);
                mapperAcoountDTO(account);
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
            mapperAcoountDTO(account);

            Account updatedAccount = accountRepository.save(account);
            modelMapper.map(updatedAccount, accountDTO);
            return accountDTO;

        }
        throw new BaseException(new ErrorMessage(ErrorsType.NO_DATA_FOUND,id.toString()));
    }

    @Override
    public AccountDTO getAccountById(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if(accountOptional.isPresent()){
            Account account = accountOptional.get();
            return mapperAcoountDTO(account);
        }
        throw new BaseException(new ErrorMessage(ErrorsType.NO_DATA_FOUND,id.toString()));
    }

    @Override
    public List<AccountDTO> getAllAccount() {
        List<AccountDTO> accountDTOList = new ArrayList<>();
        List<Account> accounts = accountRepository.findAll();
        for (Account account : accounts) {
            AccountDTO accountDTO = new AccountDTO();
            modelMapper.map(account,accountDTO);
            accountDTOList.add(accountDTO);
            accountDTOList.add(mapperAcoountDTO(account));
        }
        return accountDTOList;
    }

    @Override
    public List<AccountDTO> getAccountByUserId(Long userId) {
        System.out.println("User ID: " + userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Bele bir istifadeci yoxdur"));
        List<AccountDTO> accountDTOList = new ArrayList<>();
        List<Account> accounts = accountRepository.findAllByUser(user);
        System.out.println("Accounts found: " + accounts.size());
        for (Account account : accounts) {
            AccountDTO accountDTO = new AccountDTO();
            modelMapper.map(account, accountDTO);
            accountDTOList.add(accountDTO);
            accountDTOList.add(mapperAcoountDTO(account));
        }
        System.out.println("Account DTOs created: " + accountDTOList.size());
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
            throw new BaseException(new ErrorMessage(ErrorsType.INVALID_OPERATION, fromAccount.getIban()));
        }
        if (toAccount.getStatus() == Status.PENDING || toAccount.getStatus() == Status.BLOCKED) {
            throw new BaseException(new ErrorMessage(ErrorsType.INVALID_OPERATION, toAccount.getIban()));
        }

        if(fromAccount.getBalance().compareTo(BigDecimal.ZERO) == 0 ||
                fromAccount.getBalance().compareTo(accountTransferDTO.getAmount()) < 0 ) {
           throw new BaseException(new ErrorMessage(ErrorsType.NO_BALANCE, fromAccount.getIban()));
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(accountTransferDTO.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(accountTransferDTO.getAmount()));
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        return "Success transfer";
    }


    private AccountDTO mapperAcoountDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        modelMapper.map(account, accountDTO);
        accountDTO.setUserId(account.getUser().getId());
        accountDTO.setBranchId(account.getBranch().getId());
        return accountDTO;
    }
}
