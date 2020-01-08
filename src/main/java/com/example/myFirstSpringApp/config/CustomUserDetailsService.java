package com.example.myFirstSpringApp.config;

import com.example.myFirstSpringApp.model.User;
import com.example.myFirstSpringApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        User user = userRepository.findByUserNameOrEmail(usernameOrEmail,usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Username was not found with username or email "+usernameOrEmail)
                );

        UserPrincipal userPrincipal = UserPrincipal.create(user);
        populateUserAuthorities(user, userPrincipal);

        return userPrincipal;
    }


    @Transactional
    public UserDetails loadUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Username was not found with id "+id )
                );

        UserPrincipal userPrincipal = UserPrincipal.create(user);
        populateUserAuthorities(user, userPrincipal);

        return userPrincipal;
    }

    private void populateUserAuthorities(User user, UserPrincipal userPrincipal) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // obtain the roles the user has
        user.getUserRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRole().getName().name()));
        });

        userPrincipal.setAuthorities(authorities);
    }

}