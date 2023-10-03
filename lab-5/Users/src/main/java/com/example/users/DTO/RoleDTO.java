package com.example.users.DTO;

import com.example.users.Entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleDTO {
    private Long id;
    private String name;
    public RoleDTO(String name){
        this.name = name;
    }
    public RoleDTO(Role role){
        id = role.getId();
        name = role.getName();
    }
}
