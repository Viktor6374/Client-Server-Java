package com.example.cats.DTO;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.cats.Entity.Cat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CatDTO {
    public CatDTO(Long id, String name, String breed, String color, Long ownerID, List<Long> friendsOfCat){
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.color = color;
        this.ownerID = ownerID;
        friendsOfCat = new ArrayList<Long>(friendsOfCat);
    }
    public CatDTO(Cat cat){
        id = cat.getId();
        name = cat.getName();
        breed = cat.getBreed();
        color = cat.getColor().name();
        ownerID = cat.getOwnerId();
        friendsOfCat = new ArrayList<>();
        for (Cat cat1: cat.getFriendsOfCat()){
            friendsOfCat.add(cat1.getId());
        }
    }
    private Long id;
    private String name;
    private String breed;
    private String color;
    private Long ownerID;
    private List<Long> friendsOfCat;
}

