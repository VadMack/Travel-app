package com.vadmack.authserver.controller;

import com.vadmack.authserver.config.security.JwtTokenUtil;
import com.vadmack.authserver.domain.dto.AuthRequest;
import com.vadmack.authserver.domain.dto.UserDto;
import com.vadmack.authserver.domain.entity.User;
import com.vadmack.authserver.domain.entity.UserType;
import com.vadmack.authserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid AuthRequest request) {
        User user = userService.createUser(request, UserType.PASSWORD);
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        jwtTokenUtil.generateAccessToken(user)
                )
                .body(modelMapper.map(user, UserDto.class));
    }

    @PostMapping("/auth")
    public ResponseEntity<UserDto> login(@RequestBody @Valid AuthRequest request) {
        Authentication authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(), request.getPassword()
                        )
                );
        User user = (User) authenticate.getPrincipal();
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        jwtTokenUtil.generateAccessToken(user)
                )
                .body(modelMapper.map(user, UserDto.class));
    }

    @GetMapping("/oauth2/github")
    public ResponseEntity<UserDto> loginWithGithub(OAuth2AuthenticationToken principal) {
        User user = userService.checkIsExistsOrCreate(principal.getPrincipal()
                .getAttribute("login"), UserType.GITHUB);
        System.out.println(user);
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        jwtTokenUtil.generateAccessToken(user)
                )
                .body(modelMapper.map(user, UserDto.class));
    }

    // fixme: endpoint for test
    @GetMapping("/bebra")
    public String bebra(){
        return  "bebra";
    }
}
