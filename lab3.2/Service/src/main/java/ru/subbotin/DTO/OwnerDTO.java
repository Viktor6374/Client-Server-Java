package ru.subbotin.DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import ru.subbotin.entity.Cat;
import ru.subbotin.entity.Owner;

@Getter
public class OwnerDTO {
    public OwnerDTO(Owner owner){
        name = owner.getName();
        dateOfBirth = owner.getDateOfBirth();
        cats = new ArrayList<>();
        for (Cat cat: owner.getCats()){
            cats.add(cat.toString());
        }
    }
    private final String name;
    private final LocalDate dateOfBirth;
    private final List<String> cats;
}
