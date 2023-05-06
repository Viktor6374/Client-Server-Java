package ru.subbotin.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.subbotin.entity.Cat;

@Entity
@Table(name = "owners")
@NoArgsConstructor
public class Owner {
    public Owner(String name_){
        name = name_;
        cats = new ArrayList<>();
    }

    public Owner(Integer id_, String name_){
        id = id_;
        name = name_;
        cats = new ArrayList<>();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;
    @Getter
    @Column(name = "Name")
    private String name;
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
