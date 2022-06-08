package com.vadmack.authserver.service;

import com.vadmack.authserver.security.SecurityConfig;
import com.vadmack.authserver.domain.dto.RegistrationRequest;
import com.vadmack.authserver.domain.dto.UserDto;
import com.vadmack.authserver.domain.dto.UserDtoForUpdate;
import com.vadmack.authserver.domain.dto.UserNoIdDto;
import com.vadmack.authserver.domain.entity.Role;
import com.vadmack.authserver.domain.entity.User;
import com.vadmack.authserver.domain.entity.UserType;
import com.vadmack.authserver.exception.NotFoundException;
import com.vadmack.authserver.exception.ValidationException;
import com.vadmack.authserver.repository.UserRepository;
import com.vadmack.authserver.util.PageableService;
import com.vadmack.authserver.util.SequenceGeneratorService;
import com.vadmack.authserver.util.SortService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityConfig securityConfig;
    private final PageableService pageableService;
    private final SortService sortService;
    private final ModelMapper modelMapper = new ModelMapper();


    public void createUser(UserNoIdDto userNoIdDto) {
        validateAuthorities(userNoIdDto);
        User user = dtoToEntity(userNoIdDto);
        user.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        user.setPassword(passwordEncoder.encode(userNoIdDto.getPassword()));
        user.setUserType(UserType.PASSWORD);
        userRepository.save(user);
    }

    public List<UserDto> findList(
            @Nullable String usernamePart,
            @Nullable Integer pageNumber,
            @Nullable Integer pageSize,
            @Nullable String[] sortBy
    ) {
        if (usernamePart == null) {
            usernamePart = "";
        }
        pageableService.validateParams(pageNumber, pageSize);
        Sort sort = sortService.createSort(sortBy);
        if (pageNumber != null && pageSize != null) {
            if (sortBy == null) {
                sortBy = new String[1];
                sortBy[0] = "id:0";
            }
            return userRepository.findAllByUsernameLikeIgnoreCase(
                    usernamePart,
                    PageRequest.of(pageNumber, pageSize, sort))
                    .stream().map(this::entityToDto)
                    .collect(Collectors.toList());
        } else {
            return userRepository.findAllByUsernameLikeIgnoreCase(usernamePart, sort)
                    .stream().map(this::entityToDto)
                    .collect(Collectors.toList());
        }
    }

    public UserDto findById(Long id) {
        return entityToDto(getById(id));
    }

    public void updateUser(Long id, @Valid UserDtoForUpdate userDto) {
        User user = getById(id);

        String username = userDto.getUsername();
        Optional<User> existedUser = userRepository.findByUsername(userDto.getUsername());
        if (existedUser.isPresent() && !existedUser.get().getId().equals(id)) {
            throw new ValidationException(format("User with username '%s' already exists", username));
        } else {
            user.setUsername(username);
        }

        String password = userDto.getPassword();
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User createUser(RegistrationRequest request, UserType userType, Set<Role> authorities, Boolean enabled) {
        User user = new User();
        user.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        user.setUsername(request.getUsername());
        user.setAuthorities(authorities);
        user.setUserType(userType);
        user.setEnabled(enabled);
        user.setPassword(securityConfig.passwordEncoder().encode(request.getPassword()));
        user.setEmail(request.getEmail());
        return userRepository.insert(user);
    }

    public User createUser(String username, UserType userType, Set<Role> authorities, Boolean enabled) {
        User user = new User(
                sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME),
                username,
                authorities,
                enabled,
                userType
        );
        return userRepository.insert(user);
    }

    public User checkDoesNotExistAndCreate(RegistrationRequest request, UserType userType, Set<Role> authorities, Boolean enabled) {
        Optional<User> optionalUser = findByUsernameAndUserType(request.getUsername(), userType);
        if (optionalUser.isPresent()) {
            throw new ValidationException(String.format("User with username=%s already exists", request.getUsername()));
        }
        optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            throw new ValidationException(String.format("User with email=%s already exists", request.getEmail()));
        }
        return createUser(request, userType, authorities, enabled);
    }

    public User findExistedOrCreate(String username, UserType userType, Set<Role> authorities, Boolean enabled) {
        Optional<User> optionalUser = findByUsernameAndUserType(username, userType);
        return optionalUser.orElseGet(() -> createUser(username, userType, authorities, enabled));
    }

    private Optional<User> findByUsernameAndUserType(String username, UserType userType) {
        return userRepository.findByUsernameAndUserType(username, userType);
    }

    public void deleteUser(Long id) {
        User user = getById(id);
        userRepository.delete(user);
    }

    private User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%d not found", id)));
    }

    public User getByEmailAndUseType(String email, UserType userType) {
        return userRepository.findByEmailAndUserType(email, userType)
                .orElseThrow(() -> new NotFoundException(String.format("User with email=%s not found", email)));
    }

    private UserDto entityToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private User dtoToEntity(UserNoIdDto dto) {
        return modelMapper.map(dto, User.class);
    }

    private User dtoToEntity(UserDtoForUpdate dto) {
        return modelMapper.map(dto, User.class);
    }

    private void validateAuthorities(UserNoIdDto userNoIdDto) {
        userNoIdDto.getAuthorities().forEach(role -> {
            String strRole = role.getAuthority();
            if (!strRole.equals(Role.ROLE_USER) &&
                    (!strRole.equals(Role.ROLE_ADMIN))) {
                throw new ValidationException("Each authority should be one of the following: " +
                        Role.getAvailableRolesAsString());
            }
        });
    }

    public boolean hasPermissionToUpdate(Long userToChangeId, Long userId) {
        return userToChangeId.equals(userId);
    }

}
