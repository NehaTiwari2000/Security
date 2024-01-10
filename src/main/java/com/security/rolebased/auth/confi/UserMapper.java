package com.security.rolebased.auth.confi;

import com.security.rolebased.auth.entity.User;
import com.security.rolebased.auth.repo.IRoleRoutingRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
public class UserMapper implements UserDetails {

    private String email;
    private String password;
    private List<GrantedAuthority> roles;

    @Autowired
    private IRoleRoutingRepo roleRoutingRepo;

    public UserMapper(User user){
        this.email=user.getEmail();
        this.password=user.getPassword();
//        log.info("this.roleRoutingRepo.findAll() : {}",this.roleRoutingRepo.findAll());
//        this.roles=this.roleRoutingRepo.findAll()
//                .stream().map(roleRouting -> new String("ROLE_"+roleRouting.getRole()))
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
        this.roles= Arrays.stream((new String("ROLE_"+user.getRole())).split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        log.info("roles after setting : {}",this.roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
