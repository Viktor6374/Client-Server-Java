package ru.subbotin.DTO;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import ru.subbotin.Color;
import ru.subbotin.entity.Cat;

@Getter
@Setter
public class CatDTO {
    public CatDTO(Cat cat){
        id = cat.getId();
        name = cat.getName();
        breed = cat.getBreed();
        color = cat.getColor();
        dateOfBirth = cat.getDateOfBirth();
        owner = cat.getOwner().toString();
        friendsOfCat = new ArrayList<>();
        for (Cat cat1: cat.getFriendsOfCat()){
            friendsOfCat.add(new CatDTO(cat1));
        }
    }
    private Integer id;
    private String name;
    private String breed;
    private Color color;
    private LocalDate dateOfBirth;
    private String owner;
    private List<CatDTO> friendsOfCat;
}
