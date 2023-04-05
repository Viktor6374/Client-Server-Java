package ru.subbotin.dao;

import java.util.ArrayList;
import ru.subbotin.models.Cat;
import ru.subbotin.models.Owner;

public interface Dao {
  public void saveOwner(Owner owner);
  public void updateOwner(Owner owner);
  public void deleteOwner(Owner owner);
  public Owner findOwnerByID(int id);
  public Cat findCatByID(int id);
  public ArrayList<Owner> getAllOwners();
  public void addFriendship(Cat friend1, Cat friend2);
}
