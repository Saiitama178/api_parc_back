package com.parc.api;

import com.parc.api.model.entity.Role;
import com.parc.api.model.entity.Utilisateur;
import com.parc.api.security.CustomUserDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomUserDetailTest {
    private Utilisateur utilisateur;
    private CustomUserDetail customUserDetail;

    @BeforeEach
    void setUp() {
        Role role = new Role();
        role.setId(1);
        role.setLibRole("Administrateur");

        utilisateur = new Utilisateur();
        utilisateur.setId(1);
        utilisateur.setPseudo("testuser");
        utilisateur.setEmail("testuser@example.com");
        utilisateur.setMdp("password123");
        utilisateur.setIsActive(true);
        utilisateur.setIdRole(role);

        customUserDetail = new CustomUserDetail(utilisateur);
    }
    @Test
    void testGetAuthorities() {
        assertNotNull(customUserDetail.getAuthorities());
        assertEquals(1, customUserDetail.getAuthorities().size());
        assertEquals("Administrateur", customUserDetail.getAuthorities().iterator().next().getAuthority());
    }
    @Test
    void testGetPassword() {
        assertEquals("password123", customUserDetail.getPassword());
    }
    @Test
    void testGetUsername() {
        assertEquals("testuser@example.com", customUserDetail.getUsername());
    }
    @Test
    void testIsAccountNonExpired() {
        assertEquals(true, customUserDetail.isAccountNonExpired());
    }
    @Test
    void testIsAccountNonLocked() {
        assertEquals(true, customUserDetail.isAccountNonLocked());
    }
    @Test
    void testIsCredentialsNonExpired() {
        assertEquals(true, customUserDetail.isCredentialsNonExpired());
    }
    @Test
    void testIsEnabled() {
        assertEquals(true, customUserDetail.isEnabled());
    }

}
