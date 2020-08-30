package com.marcoagpegoraro.springsecurityjwt.service;

import com.marcoagpegoraro.springsecurityjwt.model.UserDetailsImpl;
import com.marcoagpegoraro.springsecurityjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String userName) throws UsernameNotFoundException {
        return loadUserByUsernameOptional(userName).get();
    }

    private Optional<UserDetailsImpl> loadUserByUsernameOptional(String userName) {
        return userRepository.findByUsername(userName);
    }

    public UserDetailsImpl loadUserByUsernameAndPassword(final String userName, final String password) throws UsernameNotFoundException {
        final String hashpw = getHashedPassword(password);

        final Optional<UserDetailsImpl> userOptional = loadUserByUsernameOptional(userName);

        if (!userOptional.isPresent())
            throw new UsernameNotFoundException("Usuário não encontrado.");

        final UserDetailsImpl user = userOptional.get();
        if(BCrypt.checkpw(password, user.getPassword()))
            return user;
        else
            throw new UsernameNotFoundException("Senha incorreta.");
    }

    public UserDetailsImpl signIn(final UserDetailsImpl user){
        final Optional<UserDetailsImpl> userFound = loadUserByUsernameOptional(user.getUsername());
        if(userFound.isPresent())
            throw new RuntimeException("Usuário já existe na base de dados");

        final String password = user.getPassword();
        final String hashpw = getHashedPassword(password);
        user.setPassword(hashpw);

        return userRepository.save(user);
    }

    private String getHashedPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }
}
