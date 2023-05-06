package ru.subbotin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.subbotin.Color;
import ru.subbotin.DTO.CatDTO;
import ru.subbotin.DTO.OwnerDTO;
import ru.subbotin.dao.DaoRepositoryCats;
import ru.subbotin.dao.DaoRepositoryOwners;
import ru.subbotin.entity.Cat;
import ru.subbotin.entity.Owner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CatsServiceImpl implements CatsService{
    @Autowired
    private DaoRepositoryCats daoCat;
    @Autowired
    private DaoRepositoryOwners daoOwner;

    public void saveOwner(OwnerDTO owner){
        Optional<Owner> old_owner = daoOwner.findById(owner.getId());
        Owner new_owner;
        if (old_owner.isPresent()){
            new_owner = new Owner(owner.getId(), owner.getName());
            for (Cat cat: old_owner.get().getCats()){
                new_owner.addCat(cat);
            }
        } else {
            new_owner = new Owner(owner.getName());
        }

        daoOwner.save(new_owner);
    }

    public void saveCat(CatDTO catDTO) throws Exception {
        Optional<Owner> ownerOptional = daoOwner.findById(catDTO.getOwnerID());
        Optional<Cat> old_cat = daoCat.findById(catDTO.getId());

        if (ownerOptional.isEmpty()){
            throw new Exception("Owner is not exist");
        }

        Owner owner = ownerOptional.get();

        if (old_cat.isPresent()){
            Cat cat = new Cat(catDTO.getId(), catDTO.getName(), catDTO.getBreed(), Color.valueOf(catDTO.getColor()), owner);
            cat.setFriendsOfCat(old_cat.get().getFriendsOfCat());
            daoCat.save(cat);
        } else {
            Cat cat = new Cat(catDTO.getName(), catDTO.getBreed(), Color.valueOf(catDTO.getColor()), owner);
            owner.addCat(cat);

            daoCat.save(cat);
            daoOwner.save(owner);
        }
    }

    public void deleteOwner(int ownerID){
        daoOwner.deleteById(ownerID);
    }

    public void deleteCat(int catID){
        daoCat.deleteById(catID);
    }
    public OwnerDTO findOwnerByID(Integer id){
        Optional<Owner> result = daoOwner.findById(id);

        return result.isPresent() ? new OwnerDTO(result.get()) : null;
    }
    public CatDTO findCatByID(int id){
        Optional<Cat> result = daoCat.findById(id);
        return result.isPresent() ? new CatDTO(result.get()) : null;
    }
    public ArrayList<OwnerDTO> getAllOwners(){
        ArrayList<OwnerDTO> result = new ArrayList<>();
        List<Owner> search = daoOwner.findAll();

        for (Owner owner : search){
            result.add(new OwnerDTO(owner));
        }

        return result;
    }

    public ArrayList<CatDTO> getAllCats(){
        ArrayList<CatDTO> result = new ArrayList<>();
        List<Cat> search = daoCat.findAll();

        for (Cat cat : search){
            result.add(new CatDTO(cat));
        }

        return result;
    }

    public void addFriendship(int idFriend1, int idFriend2){
        Optional<Cat> friend1 = daoCat.findById(idFriend1);
        Optional<Cat> friend2 = daoCat.findById(idFriend2);

        if (friend1.isPresent() && friend2.isPresent()){
            friend1.get().addFriend(friend2.get());
            friend2.get().addFriend(friend1.get());
            daoCat.save(friend1.get());
            daoCat.save(friend2.get());
        }
    }

    public List<CatDTO> findCatByBreed(String breed){
        List<Cat> resultCat = daoCat.findCatByBreed(breed);
        List<CatDTO> result = new ArrayList<>();

        for (Cat cat: resultCat){
            result.add(new CatDTO(cat));
        }

        return result;
    }

    public List<OwnerDTO> findOwnersByName(String name){
        List<Owner> resultOwners = daoOwner.findOwnersByName(name);
        List<OwnerDTO> result = new ArrayList<>();

        for (Owner owner: resultOwners){
            result.add(new OwnerDTO(owner));
        }

        return result;
    }
}