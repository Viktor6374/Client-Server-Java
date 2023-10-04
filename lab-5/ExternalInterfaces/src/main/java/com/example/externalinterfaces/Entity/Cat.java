package com.example.externalinterfaces.Entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Cat {
    public Cat(Long id, String name, String breed, String color, Long ownerID, List<Long> friendsOfCat){
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.color = color;
        this.ownerID = ownerID;
        this.friendsOfCat = new ArrayList<Long>(friendsOfCat);
    }
    private Long id;
    private String name;
    private String breed;
    private String color;
    private Long ownerID;
    private List<Long> friendsOfCat;
}


