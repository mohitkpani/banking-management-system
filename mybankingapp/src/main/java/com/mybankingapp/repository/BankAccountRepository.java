package com.mybankingapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mybankingapp.entity.BankAccount;
import com.mybankingapp.entity.User;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long>{

    Optional<BankAccount>
    findByAccountNumber(String accountNumber);

    Optional<BankAccount>
    findByUser(User user);
    
    Optional<BankAccount>
    findByUserEmail(String email);

}