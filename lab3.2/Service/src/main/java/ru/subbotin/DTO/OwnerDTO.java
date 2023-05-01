package ru.subbotin.DTO;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.subbotin.entity.Cat;
import ru.subbotin.entity.Owner;

@Getter
@Setter
@NoArgsConstructor
public class OwnerDTO {
    public OwnerDTO(int id, String name, List<CatDTO> cats){
        this.id = id;
        this.name = name;
        this.cats = cats;
    }

    public OwnerDTO(Owner owner){
        id = owner.getId();
        name = owner.getName();
        cats = new ArrayList<>();
        for (Cat cat: owner.getCats()){
            cats.add(new CatDTO(cat));
        }
    }

    private Integer id;
    private String name;
    private List<CatDTO> cats;
}
