package com.vas.travelapp.migration;

import com.vas.travelapp.domain.entity.User;
import com.vas.travelapp.repository.UserRepository;
import com.vas.travelapp.util.SequenceGeneratorService;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ChangeUnit(order = "1", id = "createOleg", author = "VMakarov")
@RequiredArgsConstructor
public class ChangeSet_1_sample {
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
