package com.example.cats.Services;

import com.example.cats.Color;
import com.example.cats.DTO.CatDTO;
import com.example.cats.Dao.DaoRepositoryCats;
import com.example.cats.Entity.Cat;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CatServiceImpl implements CatService{
    @Autowired
    private DaoRepositoryCats daoCat;

    @Override
    public void saveCat(CatDTO catDTO){
        Optional<Cat> old_cat = daoCat.findById(catDTO.getId());

        Cat cat = new Cat(catDTO);
        if (old_cat.isPresent()){
            cat.setFriendsOfCat(new HashSet<>(old_cat.get().getFriendsOfCat()));
        }

        daoCat.save(cat);
    }

    @Override
    public void deleteCats(List<Long> catID){
        for (Long id : catID){
            daoCat.deleteById(id);
        }
    }

    @Override
    public CatDTO findCatByID(Long id) {
        Optional<Cat> result = daoCat.findById(id);
        return result.isPresent() ? new CatDTO(result.get()) : null;
    }

    @Override
    public ArrayList<CatDTO> getAllCats(){
        ArrayList<CatDTO> result = new ArrayList<>();
        List<Cat> search = daoCat.findAll();

        for (Cat cat : search){
            result.add(new CatDTO(cat));
        }

        return result;
    }

    @Override
    public void addFriendship(Long idFriend1, Long idFriend2){
        Optional<Cat> friend1 = daoCat.findById(idFriend1);
        Optional<Cat> friend2 = daoCat.findById(idFriend2);

        if (friend1.isPresent() && friend2.isPresent()){
            friend1.get().addFriend(friend2.get());
            friend2.get().addFriend(friend1.get());
            daoCat.save(friend1.get());
            daoCat.save(friend2.get());
        } else {
            throw new NoSuchElementException();
        }

    }

    @Override
    public List<CatDTO> findCatByBreed(String breed){
        List<Cat> resultCat = daoCat.findCatByBreed(breed);
        List<CatDTO> result = new ArrayList<>();

        for (Cat cat: resultCat){
            result.add(new CatDTO(cat));
        }

        return result;
    }
    public boolean catExist(Long catId){
        return daoCat.existsById(catId);
    }
}
