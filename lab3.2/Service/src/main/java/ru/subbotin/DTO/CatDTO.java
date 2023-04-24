package ru.subbotin.DTO;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import ru.subbotin.Color;
import ru.subbotin.entity.Cat;

@Getter
public class CatDTO {
    public CatDTO(Cat cat){
        name = cat.getName();
        breed = cat.getBreed();
        color = cat.getColor();
        dateOfBirth = cat.getDateOfBirth();
        owner = cat.getOwner().toString();
        friendsOfCat = new ArrayList<>();
        for (Cat cat1: cat.getFriendsOfCat()){
            friendsOfCat.add(cat1.toString());
        }
    }
    private final String name;
    private final String breed;
    private final Color color;
    private final LocalDate dateOfBirth;
    private final String owner;
    private final List<String> friendsOfCat;
}
