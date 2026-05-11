package com.mybankingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mybankingapp.entity.BankAccount;
import com.mybankingapp.entity.Transaction;
import com.mybankingapp.service.BankAccountService;

@RestController
@RequestMapping("/account")
public class BankAccountController {
	
	@Autowired
	private BankAccountService bankAccountService;
	
	
	//CREATE ACCOUNT
	@PostMapping("/create/{userId}")
	public String createAccount(@PathVariable Long userId) {
		return bankAccountService.createAccount(userId);
	}
	
	//DEPOSIT MONEY
	@PostMapping("/deposit")
	public String depositMoney(@RequestParam String accountNumber,@RequestParam double amount) {
		return bankAccountService.depositMoney(accountNumber, amount);
	}
	
	//WITHDRAW MONEY
	@PostMapping("/withdraw")
	public String withdrawMoney(@RequestParam String accountNumber,@RequestParam double amount) {
		return bankAccountService.withdrawMoney(accountNumber, amount);
	}
	
	//CHECK BALANCE
	@GetMapping("/balance/{accountNumber}")
	public double checkBalance(@PathVariable String accountNumber) {
		return bankAccountService.checkBalance(accountNumber);
	}
	
	@GetMapping("/user/{email}")
	public BankAccount getAccountByEmail(@PathVariable String email){
	    return bankAccountService.getAccountByEmail(email);
	}
	
	@GetMapping("/transactions/{accountNumber}")
	public List<Transaction>
	getTransactionHistory(@PathVariable String accountNumber){
	    return bankAccountService.getTransactionHistory(accountNumber);
	}
	
	@GetMapping("/all-transactions")
	public List<Transaction>getAllTransactions(){
		return bankAccountService.getAllTransactions();
	}
}
