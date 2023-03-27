package ru.subbotin.models;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.subbotin.models.Cat;

@Entity
@Table(name = "Owners")
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
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
