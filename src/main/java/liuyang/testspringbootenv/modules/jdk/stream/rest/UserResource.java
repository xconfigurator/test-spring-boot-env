package liuyang.testspringbootenv.modules.jdk.stream.rest;

import liuyang.testspringbootenv.modules.jdk.stream.domain.PageableResult;
import liuyang.testspringbootenv.modules.jdk.stream.domain.User;
import liuyang.testspringbootenv.modules.jdk.stream.domain.dto.AddUserDTO;
import liuyang.testspringbootenv.modules.jdk.stream.domain.dto.UpdateUserDTO;
import liuyang.testspringbootenv.modules.jdk.stream.domain.dto.UserDTO;
import liuyang.testspringbootenv.modules.jdk.stream.repo.UserRepo;
import liuyang.testspringbootenv.modules.jdk.stream.util.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserResource {
    private final UserRepo userRepo;

    @GetMapping("/users")
    public PageableResult<User> getAllUsers(Pageable pageable) {
        val userPageable = userRepo.findAll(pageable);
        return new PageableResult<>(userPageable.getTotalElements(), userPageable.getContent());
    }

    @Transactional(readOnly = true)
    @GetMapping("/users/by-age/{age}")
    public List<UserDTO> getUsersByRoleName(@PathVariable Integer age) {
        return userRepo.findByAgeGreaterThan(age)
                .map(user -> UserDTO.builder()
                        .name(user.getName())
                        .enabled(String.valueOf(user.isEnabled()))
                        .mobile(user.getMobile())
                        .username(user.getUsername())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        return userRepo.findOptionalByUsername(username)
                .map(mapUserToDto())
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> addUser(@RequestBody AddUserDTO addUserDTO) {
        if(userRepo.findOptionalByUsername(addUserDTO.getUsername()).isPresent()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
        val toAdd = User.builder()
                .password(addUserDTO.getPassword())
                .enabled(addUserDTO.getEnabled())
                .username(addUserDTO.getUsername())
                .name(addUserDTO.getName())
                .mobile(addUserDTO.getMobile())
                .email(addUserDTO.getEmail())
                .build();
        val saved = userRepo.save(toAdd);
        return mapUserToDto().apply(saved);
    }

    @PutMapping("/users/{username}")
    public ResponseEntity<UserDTO> updateUserByUsername(@PathVariable String username, @RequestBody UpdateUserDTO updateUserDTO) {
        return userRepo.findOptionalByUsername(username)
                .map(saveUser(updateUserDTO))
                .map(mapUserToDto())
                .orElse(ResponseEntity.notFound().build());
    }

    private Function<User, ResponseEntity<UserDTO>> mapUserToDto() {
        return user -> {
            val dto = UserDTO.builder()
                    .username(user.getUsername())
                    .name(user.getName())
                    .enabled(user.isEnabled() ? "激活" : "禁用")
                    .mobile(user.getMobile())
                    .build();
            return ResponseEntity.ok().body(dto);
        };
    }

    @DeleteMapping("/users/{username}")
    public void deleteUserByUsername(@PathVariable String username) {
        userRepo.findOptionalByUsername(username)
                .map(User::getId)
                .ifPresent(userRepo::deleteById);
    }

    private Function<User, User> saveUser(UpdateUserDTO updateUserDTO) {
        return user -> {
            val toSave = user
                    .withMobile(updateUserDTO.getMobile())
                    .withName(updateUserDTO.getName())
                    .withEmail(updateUserDTO.getEmail());
            return userRepo.save(toSave);
        };
    }
}
