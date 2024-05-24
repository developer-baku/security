package org.services.spring_security.service;

import org.services.spring_security.entities.Authority;
import org.services.spring_security.entities.User;
import org.services.spring_security.repository.RepositoryG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyService implements UserDetailsService{
    @Autowired
    private RepositoryG repositoryG;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> op = repositoryG.findByUserName(username);
        if (op.isPresent()) {
            List <String> s = op.get().getAuthorities().stream().map(Authority::getAuthority).toList();
            String [] strings = s.toArray(new String[0]);
           List<SimpleGrantedAuthority> ss =  op.get().getAuthorities().stream().map(f->new SimpleGrantedAuthority(f.getAuthority())).toList();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(username)
                    .password(op.get().getPassword())
                    .authorities(ss)
                    .build();
        }
        throw new IllegalStateException();

    }
}
