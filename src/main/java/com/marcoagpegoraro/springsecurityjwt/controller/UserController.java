package com.marcoagpegoraro.springsecurityjwt.controller;

import com.marcoagpegoraro.springsecurityjwt.model.AuthenticationRequest;
import com.marcoagpegoraro.springsecurityjwt.model.AuthenticationResponse;
import com.marcoagpegoraro.springsecurityjwt.util.ResponseUtil;
import com.marcoagpegoraro.springsecurityjwt.model.UserDetailsImpl;
import com.marcoagpegoraro.springsecurityjwt.service.UserDetailsServiceImpl;
import com.marcoagpegoraro.springsecurityjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception {
        final UserDetails userDetails = userDetailsService.loadUserByUsernameAndPassword(request.getUsername(), request.getPassword());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> createUser(@RequestBody UserDetailsImpl user) throws Exception {
        final UserDetailsImpl createdUser = userDetailsService.signIn(user);

        return ResponseEntity.ok(new ResponseUtil<>("Usuário " + createdUser.getUsername() + " criado com sucesso."));
    }
}
