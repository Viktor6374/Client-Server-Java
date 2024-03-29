package com.example.externalinterfaces.Entity;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Owner {
    public Owner(Long id, String name, List<Cat> cats){
        this.id = id;
        this.name = name;
        this.cats = cats;
    }

    private Long id;
    private String name;
    private List<Cat> cats;
}
