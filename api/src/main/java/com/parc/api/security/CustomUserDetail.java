package com.parc.api.security;

import com.parc.api.model.entity.Utilisateur;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@RequiredArgsConstructor
public class CustomUserDetail implements UserDetails {

    private final Utilisateur utilisateur;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(utilisateur.getIdRole().getLibRole()));

        // Ajout des autorisations héritées
        if (utilisateur.getIdRole().getLibRole().equals("Administrateur")) {
            authorities.add(new SimpleGrantedAuthority("Utilisateur"));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return utilisateur.getMdp();
    }

    @Override
    public String getUsername() {
        return utilisateur.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return utilisateur.getIsActive();
    }

    public Integer getId() {
        return utilisateur.getId();
    }

}
