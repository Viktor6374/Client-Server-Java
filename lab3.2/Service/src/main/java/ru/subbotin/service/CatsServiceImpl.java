package ru.subbotin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        Owner new_owner = new Owner(owner.getName(), owner.getDateOfBirth());
        Optional<Owner> old_owner = daoOwner.findById(owner.getId());
        if (old_owner.isPresent()){
            for (Cat cat: old_owner.get().getCats()){
                new_owner.addCat(cat);
            }
        }

        daoOwner.save(new_owner);
    }
    public void deleteOwner(int ownerID){
        daoOwner.deleteById(ownerID);
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
}