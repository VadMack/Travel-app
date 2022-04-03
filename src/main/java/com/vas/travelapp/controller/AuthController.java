package com.vas.travelapp.controller;


import com.vas.travelapp.config.security.JwtTokenUtil;
import com.vas.travelapp.domain.dto.AuthRequest;
import com.vas.travelapp.domain.dto.UserDto;
import com.vas.travelapp.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/public")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final ModelMapper modelMapper = new ModelMapper();

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
}
