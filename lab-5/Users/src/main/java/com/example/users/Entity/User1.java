package com.example.users.Entity;
import com.example.users.DTO.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "\"users\"")
public class User1{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Getter
    @Setter
    private String password;
    @Getter
    private String username;
    @Getter
    @OneToOne
    private Owner owner;
    @Getter
    @Setter
    @ManyToOne
    private Role role;
    public User1(String password_, Owner owner_, Role role_){
        password = password_;
        owner = owner_;
        role = role_;
        username = owner_.getId().toString();
    }

    public User1() {

    }
}

