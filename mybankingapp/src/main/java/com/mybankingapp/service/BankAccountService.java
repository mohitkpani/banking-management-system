package com.mybankingapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mybankingapp.entity.BankAccount;
import com.mybankingapp.entity.Transaction;
import com.mybankingapp.entity.User;
import com.mybankingapp.repository.BankAccountRepository;
import com.mybankingapp.repository.TransactionRepository;
import com.mybankingapp.repository.UserRepository;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TransactionRepository transactionRepo;



    // CREATE ACCOUNT

    public String createAccount(Long userId) {

        Optional<User> optionalUser =
                userRepo.findById(userId);

        if(optionalUser.isEmpty()) {

            return "User not found";
        }

        User user = optionalUser.get();

        BankAccount account =
                new BankAccount();

        account.setAccountNumber(
                "ACC" + System.nanoTime());

        account.setBalance(0.0);

        account.setUser(user);

        bankAccountRepo.save(account);

        return "Bank Account Created Successfully : "
                + account.getAccountNumber();
    }



    // DEPOSIT MONEY

    public String depositMoney(
            String accountNumber,
            double amount) {

        Optional<BankAccount> optionalAccount =
                bankAccountRepo
                .findByAccountNumber(accountNumber);

        if(optionalAccount.isEmpty()) {

            return "Account not found";
        }

        if(amount <= 0) {

            return "Invalid Amount";
        }

        BankAccount account =
                optionalAccount.get();

        account.setBalance(
                account.getBalance() + amount);

        bankAccountRepo.save(account);



        // SAVE TRANSACTION

        Transaction transaction =
                new Transaction();

        transaction.setAccountNumber(
                accountNumber);

        transaction.setTransactionType(
                "DEPOSIT");

        transaction.setAmount(amount);

        transaction.setTransactionTime(
                LocalDateTime.now());

        transactionRepo.save(transaction);



        // SEND EMAIL

        User user = account.getUser();

        String subject =
                "Money Deposited Successfully";

        String body =
                "Dear " + user.getName() +

                "\n\n₹ " + amount +
                " has been deposited into your account successfully." +

                "\n\nAvailable Balance : ₹ "
                + account.getBalance() +

                "\n\nThank You for banking with us,\nMyBankingApp";

        emailService.sendEmail(
                user.getEmail(),
                subject,
                body);

        return "₹ " + amount
                + " deposited successfully";
    }



    // WITHDRAW MONEY

    public String withdrawMoney(
            String accountNumber,
            double amount) {

        Optional<BankAccount> optionalAccount =
                bankAccountRepo
                .findByAccountNumber(accountNumber);

        if(optionalAccount.isEmpty()) {

            return "Account not found";
        }

        if(amount <= 0) {

            return "Invalid Amount";
        }

        BankAccount account =
                optionalAccount.get();

        if(account.getBalance() < amount) {

            return "Insufficient Balance";
        }

        account.setBalance(
                account.getBalance() - amount);

        bankAccountRepo.save(account);



        // SAVE TRANSACTION

        Transaction transaction =
                new Transaction();

        transaction.setAccountNumber(
                accountNumber);

        transaction.setTransactionType(
                "WITHDRAW");

        transaction.setAmount(amount);

        transaction.setTransactionTime(
                LocalDateTime.now());

        transactionRepo.save(transaction);



        // SEND EMAIL

        User user = account.getUser();

        String subject =
                "Money Withdrawn Successfully";

        String body =
                "Dear " + user.getName() +

                "\n\n₹ " + amount +
                " has been withdrawn from your account successfully." +

                "\n\nAvailable Balance : ₹ "
                + account.getBalance() +

                "\n\nThank You for banking with us,\nMyBankingApp";

        emailService.sendEmail(
                user.getEmail(),
                subject,
                body);

        return "₹ " + amount
                + " withdrawn successfully";
    }



    // CHECK BALANCE

    public double checkBalance(
            String accountNumber) {

        Optional<BankAccount> optionalAccount =
                bankAccountRepo
                .findByAccountNumber(accountNumber);

        if(optionalAccount.isEmpty()) {

            return 0.0;
        }

        BankAccount account =
                optionalAccount.get();

        return account.getBalance();
    }



    // GET ACCOUNT BY EMAIL

    public BankAccount getAccountByEmail(String email) {
        Optional<BankAccount> optionalAccount = bankAccountRepo.findByUserEmail(email);

        if(optionalAccount.isPresent()) {

            return optionalAccount.get();
        }

        return null;
    }



    // TRANSACTION HISTORY

    public List<Transaction> getTransactionHistory(String accountNumber){
        return transactionRepo.findByAccountNumber(accountNumber);
    }
    
    // TRANSACTION HISTORY FOR ADMIN
    
    public List<Transaction>
    getAllTransactions(){

        return transactionRepo.findAll();
    }

}