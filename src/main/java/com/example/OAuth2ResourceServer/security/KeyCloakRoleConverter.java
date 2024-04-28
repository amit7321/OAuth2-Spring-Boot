package com.example.OAuth2ResourceServer.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Collectors;

public class KeyCloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        Map<String, Object> realmAccesss = (Map<String, Object>) jwt.getClaims().get("realm_accesss");

        if (realmAccesss == null || realmAccesss.isEmpty()) {
            return new ArrayList<>();
        }

        Collection<GrantedAuthority> roles = ((List<String>) realmAccesss.get("roles"))
                .stream()
                .map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return roles;
    }
}
