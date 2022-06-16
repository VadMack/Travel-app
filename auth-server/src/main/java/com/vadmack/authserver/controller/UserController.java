package com.vadmack.authserver.controller;

import com.vadmack.authserver.domain.dto.UserDto;
import com.vadmack.authserver.domain.dto.UserDtoForUpdate;
import com.vadmack.authserver.domain.dto.UserNoIdDto;
import com.vadmack.authserver.domain.entity.User;
import com.vadmack.authserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> findList(
            @RequestParam(value = "filterUsername", required = false) String username,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(value = "sort", defaultValue = "id:0") String[] sortBy
    ) {
        return ResponseEntity.ok(userService.findList(username, pageNumber, pageSize, sortBy));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserNoIdDto userNoIdDto) {
        userService.createUser(userNoIdDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("@userService.hasPermissionToUpdate(#id, #user.id)")
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id,
                                    @Valid @RequestBody UserDtoForUpdate userDto,
                                    @ApiIgnore @AuthenticationPrincipal User user) {
        userService.updateUser(id, userDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("@userService.hasPermissionToUpdate(#id, #user.id)")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id,
                                    @ApiIgnore @AuthenticationPrincipal User user) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
