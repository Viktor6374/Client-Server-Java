package ru.subbotin.DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import ru.subbotin.entity.Cat;
import ru.subbotin.entity.Owner;

@Getter
@Setter
public class OwnerDTO {
    public OwnerDTO(Owner owner){
        id = owner.getId();
        name = owner.getName();
        dateOfBirth = owner.getDateOfBirth();
        cats = new ArrayList<>();
        for (Cat cat: owner.getCats()){
            cats.add(new CatDTO(cat));
        }
    }

    private Integer id;
    private String name;
    private LocalDate dateOfBirth;
    private List<CatDTO> cats;
}
