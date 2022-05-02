package com.vadmack.authserver.service;

import com.vadmack.authserver.config.security.SecurityConfig;
import com.vadmack.authserver.domain.dto.AuthRequest;
import com.vadmack.authserver.domain.entity.Role;
import com.vadmack.authserver.domain.entity.User;
import com.vadmack.authserver.domain.entity.UserType;
import com.vadmack.authserver.repository.UserRepository;
import com.vadmack.authserver.util.SequenceGeneratorService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Data
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityConfig securityConfig;
    private final ModelMapper modelMapper = new ModelMapper();

    public User createUser(AuthRequest request, UserType userType) {
        User user = new User(
                sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME),
                request.getUsername(),
                securityConfig.passwordEncoder().encode(request.getPassword()),
                Set.of(new Role(Role.ROLE_USER)),
                true,
                userType
        );
        return userRepository.insert(user);
    }

    public User createUser(String username, UserType userType) {
        User user = new User(
                sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME),
                username,
                Set.of(new Role(Role.ROLE_USER)),
                true,
                userType
        );
        return userRepository.insert(user);
    }

    public User checkIfExistsOrCreate(AuthRequest request, UserType userType) {
        Optional<User> optionalUser = userRepository.findFirstByUsernameAndUserType(request.getUsername(),
                userType);
        return optionalUser.orElseGet(() -> createUser(request, userType));
    }

    public User checkIfExistsOrCreate(String username, UserType userType) {
        Optional<User> optionalUser = userRepository.findFirstByUsernameAndUserType(username,
                userType);
        return optionalUser.orElseGet(() -> createUser(username, userType));
    }


}
