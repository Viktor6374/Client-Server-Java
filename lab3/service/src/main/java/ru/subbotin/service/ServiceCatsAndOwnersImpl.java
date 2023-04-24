package ru.subbotin.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.subbotin.dao.DaoRepository;
import java.util.ArrayList;

@Service
public class ServiceCatsAndOwnersImpl implements ServiceCatsAndOwners{
    private final DaoRepository dao;
    public Service(@NonNull Dao dao_){
        dao = dao_;
    }

    public void saveOwner(Owner owner){
        dao.saveOwner(owner);
    }
    public void updateOwner(Owner owner){
        dao.updateOwner(owner);
    }
    public void deleteOwner(Owner owner){
        dao.deleteOwner(owner);
    }
    public OwnerDTO findOwnerByID(int id){
        return new OwnerDTO(dao.findOwnerByID(id));
    }
    public CatDTO findCatByID(int id){
        return new CatDTO(dao.findCatByID(id));
    }
    public ArrayList<OwnerDTO> getAllOwners(){
        ArrayList<OwnerDTO> result = new ArrayList<>();
        for (Owner owner: dao.getAllOwners()){
            result.add(new OwnerDTO(owner));
        }

        return result;
    }

    public void addFriendship(Cat friend1, Cat friend2){
        dao.addFriendship(friend1, friend2);
    }
}
