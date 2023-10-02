package com.example.cats.Entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.cats.Color;
import com.example.cats.DTO.CatDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import lombok.Setter;
import com.example.cats.Color;
@Entity
@Table(name = "cats")
@NoArgsConstructor
@Getter
public class Cat {
    public Cat(CatDTO dto){
        id = dto.getId();
        name = dto.getName();
        breed = dto.getBreed();
        color = Color.valueOf(dto.getColor());
        ownerId = dto.getOwnerID();
        friendsOfCat = new HashSet<>();
    }
    public Cat(String name_, String breed_, Color color_, Long ownerId_){
        name = name_;
        breed = breed_;
        color = color_;
        ownerId = ownerId_;
        friendsOfCat = new HashSet<>();
    }

    public Cat(Long id_, String name_, String breed_, Color color_, Long ownerId_){
        name = name_;
        breed = breed_;
        color = color_;
        ownerId = ownerId_;
        friendsOfCat = new HashSet<>();
        id = id_;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Breed")
    private String breed;

    @Column(name = "Color")
    @Enumerated(EnumType.STRING)
    private Color color;
    @Column(name = "OwnerId")
    private Long ownerId;

    @Setter
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "friendships", joinColumns = @JoinColumn(name = "first"), inverseJoinColumns = @JoinColumn(name = "second"))
    private Set<Cat> friendsOfCat;

    public void addFriend(Cat friend){
        friendsOfCat.add(friend);
    }
}
