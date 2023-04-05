package ru.subbotin.models;

import jakarta.persistence.Entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.subbotin.Color;

@Entity
@Table(name = "Cats")
@NoArgsConstructor
public class Cat {
  public Cat(String name_, String breed_, Color color_, LocalDate dateOfBirth_, Owner owner_){
    name = name_;
    breed = breed_;
    color = color_;
    dateOfBirth = dateOfBirth_;
    owner = owner_;
    friendsOfCat = new ArrayList<>();
  }
  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Getter
  @Column(name = "Name")
  private String name;
  @Getter
  @Column(name = "Breed")
  private String breed;
  @Getter
  @Column(name = "Color")
  @Enumerated(EnumType.STRING)
  private Color color;
  @Getter
  @Column(name = "Date of Birth")
  private LocalDate dateOfBirth;
  @Getter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Owner")
  private Owner owner;

  @Getter
  @ManyToMany
  @JoinTable(name = "Friendships", joinColumns = @JoinColumn(name = "First cat"), inverseJoinColumns = @JoinColumn(name = "Second cat"))
  private List<Cat> friendsOfCat;

  public void addFriend(Cat friend){
    friendsOfCat.add(friend);
  }
}

