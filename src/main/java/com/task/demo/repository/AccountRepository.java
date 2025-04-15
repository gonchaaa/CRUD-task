package com.task.demo.repository;

import com.task.demo.entities.Account;
import com.task.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    List<Account> findAllByUser(User user);
}
