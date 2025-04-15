package com.task.demo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.task.demo.enums.CurrencyType;
import com.task.demo.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_number",nullable = false,unique = true)
    private String accountNumber;
    @Column(name = "iban",nullable = false,unique = true)
    private String iban;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date", nullable = false)
    private LocalDate expiredDate;
    @Column(name = "balance",nullable = false)
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    @Column(name = "currency_type",nullable = false)
    private CurrencyType currencyType;
    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

}
