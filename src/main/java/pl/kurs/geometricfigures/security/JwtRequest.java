package pl.kurs.geometricfigures.security;

import java.io.Serializable;

public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
