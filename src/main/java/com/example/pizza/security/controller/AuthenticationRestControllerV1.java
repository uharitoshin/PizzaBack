package com.example.pizza.security.controller;

import com.example.pizza.security.dto.AuthenticationRequestDto;
import com.example.pizza.security.dto.UserDto;
import com.example.pizza.security.mapstract.UserMapper;
import com.example.pizza.security.model.User;
import com.example.pizza.security.repository.UserRepository;
import com.example.pizza.security.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationRestControllerV1 {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDto request) {
        try {
            // аутентицицируем пользака
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));

            User user = userRepository.findByLogin(request.getLogin()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));

            String token = jwtTokenUtils.createToken(request.getLogin(), user.getRole().name());

            Map<Object, Object> response = new HashMap<>();
            response.put("login", request.getLogin());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @PostMapping("/registration")
    public boolean registration(@RequestBody UserDto userDto) {
        var optionalUser = userRepository.findByLogin(userDto.getLogin());

        if (optionalUser.isEmpty() && userDto.getPassword() != null) {
            userDto.setPassword(new BCryptPasswordEncoder(12).encode(userDto.getPassword()));
            userRepository.save(userMapper.fromDto(userDto));
            return true;
        }
        return false;
    }
}