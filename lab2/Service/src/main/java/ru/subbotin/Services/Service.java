package ru.subbotin.Services;

import java.util.ArrayList;
import lombok.NonNull;
import ru.subbotin.DTO.CatDTO;
import ru.subbotin.DTO.OwnerDTO;
import ru.subbotin.dao.Dao;
import ru.subbotin.models.Cat;
import ru.subbotin.models.Owner;


public class Service {
  private final Dao dao;
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
