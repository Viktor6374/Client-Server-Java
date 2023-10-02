package com.example.users.Entity;

import java.util.*;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "owners")
@NoArgsConstructor
public class Owner {
    public Owner(String name_){
        name = name_;
        catsId = new HashSet<>();
    }

    public Owner(Long id_, String name_){
        id = id_;
        name = name_;
        catsId = new HashSet<>();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Getter
    @Column(name = "Name")
    private String name;
    @Column(name = "cats")
    @ElementCollection
    private HashSet<Long> catsId;

    public Set<Long> getCats(){
        return Collections.unmodifiableSet(catsId);
    }

    public void addCat(@NonNull Long catId_){
        catsId.add(catId_);
    }

    public void removeCat(@NonNull List<Long> catId_){
        for (Long id: catId_){
            catsId.remove(id);
        }
    }
}

