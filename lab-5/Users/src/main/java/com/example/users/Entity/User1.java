package com.example.users.Entity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "\"users\"")
@Getter
@Setter
public class User1{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    private String username;
    @OneToOne
    private Owner owner;
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

