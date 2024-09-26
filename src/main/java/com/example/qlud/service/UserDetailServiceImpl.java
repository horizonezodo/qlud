package com.example.qlud.service;

import com.example.qlud.model.User;
import com.example.qlud.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepo.findUserByUserEmail(username);
        Optional<User> userOpt = userRepo.findUserByPhoneNumber(username);
        if(userOptional.isPresent()){
            return UserDetailImpl.build(userOptional.get());
        }else if(userOpt.isPresent()){
            return UserDetailImpl.build(userOpt.get());
        }
        return null;
    }
}
