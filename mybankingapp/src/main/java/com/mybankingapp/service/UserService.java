package com.mybankingapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mybankingapp.entity.BankAccount;
import com.mybankingapp.entity.Role;
import com.mybankingapp.entity.User;
import com.mybankingapp.repository.BankAccountRepository;
import com.mybankingapp.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BankAccountRepository bankAccountRepo;



    // REGISTER USER
    public User registerUser(User user) {

        user.setRole(Role.USER);

        return userRepo.save(user);
    }



    // LOGIN USER
    public String loginUser(
            String email,
            String password) {

        User user =
                userRepo.findByEmail(email)
                .orElse(null);

        if(user == null) {

            return "No user found";
        }

        if(user.getPassword().equals(password)) {

            return "Login successful";

        } else {

            return "Invalid password";
        }
    }



    // GET ALL USERS
    public List<User> getAllUsers(){

        return userRepo.findAll();
    }



    // DELETE USER
    public String deleteUser(Long id) {

        Optional<User> optionalUser =
                userRepo.findById(id);

        if(optionalUser.isEmpty()) {

            return "No user found";
        }

        User user =
                optionalUser.get();

        Optional<BankAccount> optionalAccount =
                bankAccountRepo.findByUser(user);

        if(optionalAccount.isPresent()) {

            bankAccountRepo.delete(
                    optionalAccount.get());
        }

        userRepo.delete(user);

        return "User deleted successfully";
    }



    // REGISTER ADMIN
    public User registerAdmin(User user) {

        user.setRole(Role.ADMIN);

        return userRepo.save(user);
    }

}