package com.security.rolebased.auth.confi;

import com.security.rolebased.auth.entity.User;
import com.security.rolebased.auth.repo.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Configuration
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private IUserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepo.findByEmail(username);
        return user.map(UserMapper::new).
                orElseThrow(()->new UsernameNotFoundException("User not found !!!"));
    }
}
