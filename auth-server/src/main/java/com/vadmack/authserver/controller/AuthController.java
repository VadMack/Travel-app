package com.vadmack.authserver.controller;

import com.vadmack.authserver.config.security.JwtTokenUtil;
import com.vadmack.authserver.config.security.OnPasswordResetEvent;
import com.vadmack.authserver.config.security.OnRegistrationCompleteEvent;
import com.vadmack.authserver.domain.dto.*;
import com.vadmack.authserver.domain.entity.*;
import com.vadmack.authserver.service.UserService;
import com.vadmack.authserver.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/public")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();
    private final ApplicationEventPublisher eventPublisher;
    private final VerificationTokenService verificationTokenService;


    @PostMapping("/registration")
    public ResponseEntity<UserDto> registerUserAccount(
            @Valid @RequestBody RegistrationRequest request) {

        User user = userService.checkDoesNotExistAndCreate(request,
                UserType.PASSWORD, Set.of(new Role(Role.ROLE_USER)), false);

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getAppUrl()));

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        jwtTokenUtil.generateAccessToken(user)
                )
                .body(modelMapper.map(user, UserDto.class));
    }


    @GetMapping("/registration-confirm")
    public ResponseEntity<UserDto> confirmRegistration(@RequestParam String token) {

        VerificationToken verificationToken = verificationTokenService.validateToken(token, TokenType.REGISTRATION);
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userService.saveUser(user);

        String refreshToken = UUID.randomUUID().toString();
        verificationTokenService.createVerificationToken(user, refreshToken, TokenType.REFRESH);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setRefreshToken(refreshToken);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        jwtTokenUtil.generateAccessToken(user)
                )
                .body(userDto);
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
        String refreshToken = UUID.randomUUID().toString();
        verificationTokenService.createVerificationToken(user, refreshToken, TokenType.REFRESH);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setRefreshToken(refreshToken);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        jwtTokenUtil.generateAccessToken(user)
                )
                .body(userDto);
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<UserDto> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        String token = request.getToken();
        VerificationToken verificationToken = verificationTokenService.validateToken(token, TokenType.REFRESH);
        User user = verificationToken.getUser();

        String refreshToken = UUID.randomUUID().toString();
        verificationTokenService.createVerificationToken(user, refreshToken, TokenType.REFRESH);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setRefreshToken(refreshToken);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        jwtTokenUtil.generateAccessToken(user)
                )
                .body(userDto);
    }

    @GetMapping("/oauth2/github")
    public ResponseEntity<UserDto> loginWithGithub(OAuth2AuthenticationToken principal) {
        User user = userService.findExistedOrCreate(principal.getPrincipal()
                .getAttribute("login"), UserType.GITHUB, Set.of(new Role(Role.ROLE_USER)), true);
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        jwtTokenUtil.generateAccessToken(user)
                )
                .body(modelMapper.map(user, UserDto.class));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        User user = userService.getByEmailAndUseType(request.getEmail(), UserType.PASSWORD);
        eventPublisher.publishEvent(new OnPasswordResetEvent(user, request.getAppUrl()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token,
                                           @RequestBody ResetPasswordStage2Request request) {
        VerificationToken verificationToken = verificationTokenService.validateToken(token, TokenType.PASSWORD_RESET);
        User user = verificationToken.getUser();
        userService.updateUser(user.getId(),  new UserDtoForUpdate(user.getUsername(), request.getPassword()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // fixme: endpoint for test
    @GetMapping("/bebra")
    public String bebra(){
        return  "bebra";
    }
}
