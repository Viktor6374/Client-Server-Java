package com.example.users.DTO;

import com.example.users.Entity.Owner;
import com.example.users.Entity.Role;
import com.example.users.Entity.User1;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    public UserDTO(Long id, String password, String username, OwnerDTO owner, RoleDTO role){
        this.id = id;
        this.owner = owner;
        this.password = password;
        this.username = username;
        this.role = role;
    }
    public UserDTO(User1 user){
        id = user.getId();
        password = user.getPassword();
        username = user.getUsername();
        owner = new OwnerDTO(user.getOwner());
        role = new RoleDTO(user.getRole());
    }
    private Long id;
    private String password;
    private String username;
    private OwnerDTO owner;
    private RoleDTO role;
}
