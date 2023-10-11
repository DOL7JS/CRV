package cz.upce.nnpro_backend.config;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private final String username;
    private final Long id;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtResponse(String jwttoken, String username, Long id, Collection<? extends GrantedAuthority> authorities) {
        this.jwttoken = jwttoken;
        this.username = username;
        this.id = id;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public String getJwttoken() {
        return jwttoken;
    }

    public String getUsername() {
        return username;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getToken() {
        return this.jwttoken;
    }
}