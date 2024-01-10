package com.security.rolebased.auth.controller;

import com.security.rolebased.auth.entity.User;
import com.security.rolebased.auth.repo.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/save-user")
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        Optional<User> userRepoByEmail = this.userRepo.findByEmail(user.getEmail());
        if (userRepoByEmail.isPresent()) {
            return ResponseEntity.ok("User already exist");
        }
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        this.userRepo.save(user);
        return ResponseEntity.ok("User details are saved...");
    }

    @GetMapping("get-all-user")
    public ResponseEntity<?> fetchAllUser() {
        return ResponseEntity.ok("Fetched all data " + this.userRepo.findAll());
    }

    @GetMapping("get-logged-in-user-detail")
    public ResponseEntity<?> fetchLoggedInUserDetail() {
        return ResponseEntity.ok(this.userRepo.findByEmail(getLoggedInUser().getUsername()));
    }

    private UserDetails getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }

}
