package ru.subbotin;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "Cats")
@AllArgsConstructor
public class Cat {
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
}

