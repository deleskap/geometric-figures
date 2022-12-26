package pl.kurs.geometricfigures.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.kurs.geometricfigures.security.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final ModelMapper mapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody @Valid CreateUserCommand command) {
        AppUser savedUser = userDetailsService.addUser(command);
        return ResponseEntity.ok(mapper.map(savedUser, UserDto.class));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(@PageableDefault(size = 20) Pageable pageable) {
        Page<AppUser> userPage = userDetailsService.findAll(pageable);
        List<UserDto> userDtoPage = userPage.stream().map(x -> mapper.map(x, UserDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok(userDtoPage);
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<JwtResponse> refreshAuthenticationToken(@RequestBody JwtRequest jwtRequest) {
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(jwtRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

}
