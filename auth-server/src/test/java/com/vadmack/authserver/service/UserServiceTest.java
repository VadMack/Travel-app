package com.vadmack.authserver.service;

import com.vadmack.authserver.config.security.SecurityConfig;
import com.vadmack.authserver.domain.dto.RegistrationRequest;
import com.vadmack.authserver.domain.dto.UserDto;
import com.vadmack.authserver.domain.entity.User;
import com.vadmack.authserver.domain.entity.UserType;
import com.vadmack.authserver.exception.NotFoundException;
import com.vadmack.authserver.exception.ValidationException;
import com.vadmack.authserver.repository.UserRepository;
import com.vadmack.authserver.util.SequenceGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    @Mock
    private SecurityConfig securityConfig;

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void getByIdSuccess() {
        User expectedUser = getUser();

        UserDto expected = modelMapper.map(expectedUser, UserDto.class);

        when(userRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(expectedUser));

        UserDto received = userService.findById(12L);

        Assertions.assertEquals(expected, received);
    }

    @Test
    public void getByIdNotFound() {
        when(userRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> userService.findById(12L));
    }

    @Test
    public void checkDoesNotExistAndCreateSuccess() {
        PasswordEncoder pe = new BCryptPasswordEncoder();
        User expected = getUser();
        expected.setPassword(pe.encode("password"));

        when(userRepository.findByUsernameAndUserType(any(String.class), any(UserType.class)))
                .thenReturn(Optional.empty());

        when(userRepository.findByEmail(any(String.class)))
                .thenReturn(Optional.empty());

        when(userRepository.insert(any(User.class))).thenReturn(expected);

        when(sequenceGeneratorService.generateSequence(any(String.class))).thenReturn(1L);

        when(securityConfig.passwordEncoder()).thenReturn(pe);

        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("user");
        request.setPassword("password");
        request.setEmail("email");

        User received = userService.checkDoesNotExistAndCreate(
                request, UserType.PASSWORD, expected.getAuthorities(), true);

        Assertions.assertEquals(expected, received);
    }

    @Test
    public void checkDoesNotExistAndCreateAlreadyExists1() {

        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("user");
        request.setPassword("password");
        request.setEmail("email");

        when(userRepository.findByUsernameAndUserType(any(String.class), any(UserType.class)))
                .thenReturn(Optional.of(new User()));

        Assertions.assertThrows(ValidationException.class, () ->
                userService.checkDoesNotExistAndCreate(
                        request, UserType.PASSWORD, Collections.emptySet(), true));
    }

    @Test
    public void checkDoesNotExistAndCreateAlreadyExists2() {

        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("user");
        request.setPassword("password");
        request.setEmail("email");

        when(userRepository.findByUsernameAndUserType(any(String.class), any(UserType.class)))
                .thenReturn(Optional.empty());

        when(userRepository.findByEmail(any(String.class)))
                .thenReturn(Optional.of(new User()));

        Assertions.assertThrows(ValidationException.class, () ->
                userService.checkDoesNotExistAndCreate(
                        request, UserType.PASSWORD, Collections.emptySet(), true));
    }

    @Test
    public void createUserSuccess() {
        User expected = getUser();
        when(userRepository.insert(any(User.class)))
                .thenReturn(expected);

        User received = userService.createUser(expected.getUsername(),
                expected.getUserType(),
                expected.getAuthorities(),
                expected.isEnabled());

        Assertions.assertEquals(expected, received);
    }

    public User getUser(){
        User user = new User();
        user.setId(12L);
        user.setUsername("user");
        user.setUserType(UserType.PASSWORD);
        user.setEnabled(true);
        return user;
    }


}
