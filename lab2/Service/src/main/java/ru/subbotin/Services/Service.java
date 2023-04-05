package ru.subbotin.Services;

import java.util.ArrayList;
import lombok.NonNull;
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
  public Owner findOwnerByID(int id){
    return dao.findOwnerByID(id);
  }
  public Cat findCatByID(int id){
    return dao.findCatByID(id);
  }
  public ArrayList<Owner> getAllOwners(){
    return dao.getAllOwners();
  }

  public void addFriendship(Cat friend1, Cat friend2){
    dao.addFriendship(friend1, friend2);
  }
}
