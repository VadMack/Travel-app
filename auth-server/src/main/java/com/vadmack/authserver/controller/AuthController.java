package com.vadmack.authserver.controller;

import com.vadmack.authserver.config.security.JwtTokenUtil;
import com.vadmack.authserver.config.security.OnRegistrationCompleteEvent;
import com.vadmack.authserver.domain.dto.AuthRequest;
import com.vadmack.authserver.domain.dto.RegistrationRequest;
import com.vadmack.authserver.domain.dto.UserDto;
import com.vadmack.authserver.domain.entity.Role;
import com.vadmack.authserver.domain.entity.User;
import com.vadmack.authserver.domain.entity.UserType;
import com.vadmack.authserver.domain.entity.VerificationToken;
import com.vadmack.authserver.exception.ValidationException;
import com.vadmack.authserver.service.UserService;
import com.vadmack.authserver.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;
import java.util.Set;

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

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user,
                Locale.ENGLISH, request.getAppUrl()));

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        jwtTokenUtil.generateAccessToken(user)
                )
                .body(modelMapper.map(user, UserDto.class));
    }


    @GetMapping("/registration-confirm")
    public ResponseEntity<UserDto> confirmRegistration(@RequestParam String token) {

        VerificationToken verificationToken = verificationTokenService.findVerificationToken(token);

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            throw new ValidationException("Token has expired");
        }
        user.setEnabled(true);
        userService.saveUser(user);
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
        User user = userService.findExistedOrCreate(principal.getPrincipal()
                .getAttribute("login"), UserType.GITHUB, Set.of(new Role(Role.ROLE_USER)), true);
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
