package com.example.externalinterfaces.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@NoArgsConstructor
public class Role implements GrantedAuthority {
    private String name;
    public Role(String name){
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
