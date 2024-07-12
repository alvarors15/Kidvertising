package com.example.Mediateca.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Mediateca.Domain.UserDO;
import com.example.Mediateca.Repositories.UserRepository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDO usuario = userRepository.findByEmail(email);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + email);
        }

        return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getPassword(),
                getAuthorities(usuario));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(UserDO user) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        // Aquí determinas los roles basados en la instancia de la clase
        if (user.getRol().equals("ROLE_PROFESOR")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_PROFESOR"));
        } else if (user.getRol().equals("ROLE_ALUMNO")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ALUMNO"));
        }

        return authorities;
    }
}
