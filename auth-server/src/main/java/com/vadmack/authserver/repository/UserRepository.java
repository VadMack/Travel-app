package com.vadmack.authserver.repository;


import com.vadmack.authserver.domain.entity.User;
import com.vadmack.authserver.domain.entity.UserType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    List<User> findAllByUsernameLikeIgnoreCase(String namePart, Sort sort);
    List<User> findAllByUsernameLikeIgnoreCase(String namePart, Pageable pageable);

    Optional<User> findByUsernameAndUserType(String username, UserType userType);
    Optional<User> findByEmailAndUserType(String email, UserType userType);
}
