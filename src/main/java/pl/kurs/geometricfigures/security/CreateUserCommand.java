package pl.kurs.geometricfigures.security;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateUserCommand {
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private List<String> roles;

}
