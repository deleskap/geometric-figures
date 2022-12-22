package pl.kurs.geometricfigures.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.kurs.geometricfigures.repository.AppRoleRepository;
import pl.kurs.geometricfigures.repository.AppUserRepository;
import pl.kurs.geometricfigures.security.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@AllArgsConstructor
public class UserController {

    private final AppUserRepository repository;
    private final AppRoleRepository appRoleRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;

    @PostMapping("/api/v1/users")
    public ResponseEntity<UserDto> addUser(@RequestBody @Valid CreateUserCommand createUserCommand) {
        AppUser newUser = mapper.map(createUserCommand, AppUser.class);
        //check if role exists in database
        Set<AppRole> roles = createUserCommand.getRoles()
                .stream()
                .map(r -> appRoleRepository.findFirstByName(r).orElse(new AppRole(r)))
                .collect(Collectors.toSet());
        newUser.setRoles(roles);
        newUser.setPassword(passwordEncoder.encode(createUserCommand.getPassword()));
        AppUser savedUser = repository.save(newUser);
        return ResponseEntity.ok(mapper.map(savedUser, UserDto.class));
    }

    @GetMapping("/api/v1/users")
    public ResponseEntity<List<UserDto>> getUsers(@PageableDefault(page = 0, size = 20) Pageable pageable) {
        Page<AppUser> userPage = repository.findAll(pageable);
        List<UserDto> userDtoPage = userPage.stream().map(x -> mapper.map(x, UserDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok(userDtoPage);
    }

    @PostMapping(value = "/api/v1/users/refresh")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

}
