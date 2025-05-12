package com.agudinoza.parrot.security;

import com.agudinoza.parrot.model.entity.Mesero;
import com.agudinoza.parrot.repository.IMeseroRepository;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IMeseroRepository meseroRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Mesero mesero = meseroRepository.findByMail(email);
        if (mesero == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new User(mesero.getMail(), "", new ArrayList<>());
    }
}