package com.example.users.Entity;

import java.util.*;
import javax.persistence.*;

import com.example.users.DTO.OwnerDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "owners")
@NoArgsConstructor
@Getter
@Setter
public class Owner {
    public Owner(String name_){
        name = name_;
        catsId = new ArrayList<>();
    }
    public Owner(OwnerDTO dto){
        name = dto.getName();
        catsId = new ArrayList<>();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Name")
    private String name;
    @Column(name = "cats")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> catsId;

    public void addCat(@NonNull Long catId_){
        catsId.add(catId_);
    }

    public void removeCat(@NonNull Long catId_){
        catsId.remove(catId_);
    }
}

