package com.example.cloud_storage.cotroller;

import com.example.cloud_storage.dto.request.AuthenticationRQ;
import com.example.cloud_storage.dto.response.AuthenticationRS;
import com.example.cloud_storage.exception.BadCredentialsException;
import com.example.cloud_storage.jwt.JwtTokenProvider;
import com.example.cloud_storage.model.User;
import com.example.cloud_storage.repository.AuthenticationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    AuthenticationRepository authenticationRepository;

    @PostMapping("/login")
    public AuthenticationRS login(@RequestBody @Valid AuthenticationRQ request) {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getLogin(),
                            request.getPassword()
                    )
            );

            User user = (User) authentication.getPrincipal();
            String token = jwtTokenProvider.generateToken(user);
            authenticationRepository.putTokenAndUsername(token, user.getUsername());

            log.info("User {} authentication. JWT: {}", user.getUsername(), token);
            return new AuthenticationRS(token);
    }
}
