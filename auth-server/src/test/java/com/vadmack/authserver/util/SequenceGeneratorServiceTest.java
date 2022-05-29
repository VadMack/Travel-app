package com.vadmack.authserver.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoOperations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SequenceGeneratorServiceTest {

    @InjectMocks
    SequenceGeneratorService sequenceGeneratorService;

    @Mock
    MongoOperations mongoOperations;

    @Test
    public void generateSequenceSuccess() {
        DatabaseSequence counter = new DatabaseSequence();
        counter.setSeq(12);
        when(mongoOperations.findAndModify(any(), any(), any(), any(Class.class))).thenReturn(counter);
        Long received = sequenceGeneratorService.generateSequence("name");
        Assertions.assertEquals(counter.getSeq(), received);
    }

    @Test
    public void generateSequenceFirst() {
        when(mongoOperations.findAndModify(any(), any(), any(), any(Class.class))).thenReturn(null);
        Long received = sequenceGeneratorService.generateSequence("name");
        Assertions.assertEquals(1, received);
    }

}
