package ru.subbotin.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import lombok.Setter;
import ru.subbotin.Color;
@Entity
@Table(name = "cats")
@NoArgsConstructor
@Getter
public class Cat {
    public Cat(String name_, String breed_, Color color_, Owner owner_){
        name = name_;
        breed = breed_;
        color = color_;
        owner = owner_;
        owner_.addCat(this);
        friendsOfCat = new ArrayList<>();
    }

    public Cat(Integer id_, String name_, String breed_, Color color_, Owner owner_){
        name = name_;
        breed = breed_;
        color = color_;
        owner = owner_;
        owner_.addCat(this);
        friendsOfCat = new ArrayList<>();
        id = id_;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Breed")
    private String breed;

    @Column(name = "Color")
    @Enumerated(EnumType.STRING)
    private Color color;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Owner")
    private Owner owner;

    @Setter
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "friendships", joinColumns = @JoinColumn(name = "first"), inverseJoinColumns = @JoinColumn(name = "second"))
    private List<Cat> friendsOfCat;

    public void addFriend(Cat friend){
        friendsOfCat.add(friend);
    }
}