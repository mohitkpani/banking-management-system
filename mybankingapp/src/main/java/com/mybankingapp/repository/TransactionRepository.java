package com.mybankingapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mybankingapp.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	
	List<Transaction> findByAccountNumber(String accountNumber);

}
