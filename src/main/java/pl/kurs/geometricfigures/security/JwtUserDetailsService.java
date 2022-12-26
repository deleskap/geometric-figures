package pl.kurs.geometricfigures.security;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kurs.geometricfigures.repository.AppRoleRepository;
import pl.kurs.geometricfigures.repository.AppUserRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsernameWithRoles(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public AppUser addUser(CreateUserCommand command){
        AppUser newUser = mapper.map(command, AppUser.class);
        //check if role exists in database
        Set<AppRole> roles = command.getRoles()
                .stream()
                .map(r -> appRoleRepository.findFirstByName(r).orElse(new AppRole(r)))
                .collect(Collectors.toSet());
        newUser.setRoles(roles);
        newUser.setPassword(passwordEncoder.encode(command.getPassword()));
        return appUserRepository.save(newUser);
    }

    public Page<AppUser> findAll(Pageable pageable){
        return appUserRepository.findAll(pageable);
    }
}
