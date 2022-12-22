package pl.kurs.geometricfigures.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.kurs.geometricfigures.validation.Role;
import pl.kurs.geometricfigures.validation.UserNotExists;

import javax.validation.Valid;
import java.util.List;

@Getter
public class CreateUserCommand {
    @UserNotExists
    private String username;

    private String password;

    private String firstName;

    private String lastName;
    @Role
    private List<String> roles;

}
