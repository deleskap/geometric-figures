package pl.kurs.geometricfigures.security;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.kurs.geometricfigures.repository.AppRoleRepository;
import pl.kurs.geometricfigures.repository.AppUserRepository;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class UserController {

    private AppUserRepository repository;
    private AppRoleRepository appRoleRepository;
    private ModelMapper mapper;
    private PasswordEncoder passwordEncoder;

    @PostMapping("/api/v1/users")
    public ResponseEntity<UserDto> addUser(@RequestBody CreateUserCommand createUserCommand) {
        AppUser newUser = mapper.map(createUserCommand, AppUser.class);
        Set<AppRole> roles = createUserCommand.getRoles()
                .stream()
                .map(r -> appRoleRepository.findFirstByName(r).orElse(new AppRole(r)))
                .collect(Collectors.toSet());
        newUser.setRoles(roles);
        newUser.setPassword(passwordEncoder.encode(createUserCommand.getPassword()));
        AppUser savedUser = repository.save(newUser);

        return ResponseEntity.ok(new UserDto(savedUser.getId(),
                savedUser.getUsername(),
                "*SECRET*",
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toList())));
    }

}
