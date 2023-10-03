package com.example.users.DTO;

import java.util.ArrayList;
import java.util.List;

import com.example.users.Entity.Owner;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OwnerDTO {
    public OwnerDTO(Long id, String name, List<Long> cats){
        this.id = id;
        this.name = name;
        this.cats = cats;
    }

    public OwnerDTO(Owner owner){
        id = owner.getId();
        name = owner.getName();
        cats = new ArrayList<>(owner.getCatsId());
    }

    private Long id;
    private String name;
    private List<Long> cats;
}

