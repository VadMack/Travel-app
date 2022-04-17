package com.vas.travelapp.utils.migration;

import com.vas.travelapp.domain.user.User;
import com.vas.travelapp.domain.user.UserRepository;
import com.vas.travelapp.utils.mongo.SequenceGeneratorService;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Profile("production")
@ChangeUnit(order = "1", id = "createOleg", author = "VMakarov")
@RequiredArgsConstructor
public class ChangeSet1Sample {
    private final SequenceGeneratorService sequenceGeneratorService;
    private final UserRepository userRepository;

    @Execution
    public void changeSet() {
        List<User> users = new ArrayList<>();
        User oleg = new User();
        oleg.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        oleg.setUsername("Oleg-useless");
        users.add(oleg);
        userRepository.insert(users);
    }

    @RollbackExecution
    public void rollback() {
        Optional<User> oleg = userRepository.findByUsername("Oleg-useless");
        oleg.ifPresent(userRepository::delete);
    }

}
