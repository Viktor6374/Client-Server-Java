package ru.subbotin.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.subbotin.models.Cat;

@Entity
@Table(name = "Owners")
@NoArgsConstructor
public class Owner {
  public Owner(String name_, LocalDate dateOfBirth_){
    name = name_;
    dateOfBirth = dateOfBirth_;
    cats = new ArrayList<>();
  }
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Getter
  @Column(name = "Name")
  private String name;
  @Getter
  @Column(name = "Date of Birth")
  private LocalDate dateOfBirth;
  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Cat> cats;

  public List<Cat> getCats(){
    return Collections.unmodifiableList(cats);
  }

  public void addCat(@NonNull Cat cat){
    cats.add(cat);
  }

  public void removeCat(@NonNull Cat cat){
    cats.remove(cat);
  }
}
