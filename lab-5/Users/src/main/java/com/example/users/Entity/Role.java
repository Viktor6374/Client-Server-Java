package com.example.users.Entity;

import lombok.Getter;
import javax.persistence.GenerationType;
import javax.persistence.*;

@Entity
@Table(name = "\"roles\"")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Getter
    private String name;
}

