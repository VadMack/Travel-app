package com.vadmack.authserver.migration;


import com.vadmack.authserver.config.security.SecurityConfig;
import com.vadmack.authserver.domain.entity.Role;
import com.vadmack.authserver.domain.entity.User;
import com.vadmack.authserver.repository.UserRepository;
import com.vadmack.authserver.util.SequenceGeneratorService;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ChangeUnit(order = "1", id = "createUser", author = "VMakarov")
@RequiredArgsConstructor
public class ChangeSet_1_sample {
    private final SequenceGeneratorService sequenceGeneratorService;
    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;

    @Execution
    public void changeSet() {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        user.setUsername("user");
        user.setPassword(securityConfig.passwordEncoder().encode("user"));
        user.setAuthorities(Set.of(new Role(Role.ROLE_USER)));
        users.add(user);
        userRepository.insert(users);
    }

    @RollbackExecution
    public void rollback() {
        Optional<User> oleg = userRepository.findByUsername("Oleg-useless");
        oleg.ifPresent(userRepository::delete);
    }


}
