package ru.subbotin.DTO;


import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.subbotin.Color;
import ru.subbotin.entity.Cat;

@Getter
@Setter
@NoArgsConstructor
public class CatDTO {
    public CatDTO(int id, String name, String breed, String color, int ownerID){
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.color = color;
        this.ownerID = ownerID;
        friendsOfCat = new ArrayList<String>();
    }
    public CatDTO(Cat cat){
        id = cat.getId();
        name = cat.getName();
        breed = cat.getBreed();
        color = cat.getColor().name();
        ownerID = cat.getOwner().getId();
        friendsOfCat = new ArrayList<>();
        for (Cat cat1: cat.getFriendsOfCat()){
            friendsOfCat.add(cat1.toString());
        }
    }
    private Integer id;
    private String name;
    private String breed;
    private String color;
    private int ownerID;
    private List<String> friendsOfCat;
}
