package com.example.users.Services;

import com.example.users.DTO.OwnerDTO;
import com.example.users.DTO.UserDTO;
import com.example.users.Dao.DaoRepositoryOwners;
import com.example.users.Dao.DaoRepositoryRoles;
import com.example.users.Dao.DaoRepositoryUsers;
import com.example.users.Entity.Owner;
import com.example.users.Entity.Role;
import com.example.users.Entity.User1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    DaoRepositoryOwners daoOwner;
    @Autowired
    DaoRepositoryRoles daoRole;
    @Autowired
    DaoRepositoryUsers daoUser;
    @Override
    public Long changeOwner(OwnerDTO owner) {
        Optional<Owner> old_owner = daoOwner.findById(owner.getId());
        Owner new_owner;
        if (old_owner.isPresent()){
            new_owner = old_owner.get();
            new_owner.setName(owner.getName());
        } else {
            throw new NoSuchElementException("User not found");
        }

        return (daoOwner.save(new_owner)).getId();
    }

    @Override
    public OwnerDTO findOwnerByID(Long id) {
        Optional<Owner> result = daoOwner.findById(id);

        return result.isPresent() ? new OwnerDTO(result.get()) : null;
    }

    @Override
    public ArrayList<OwnerDTO> getAllOwners() {
        ArrayList<OwnerDTO> result = new ArrayList<>();
        List<Owner> search = daoOwner.findAll();

        for (Owner owner : search){
            result.add(new OwnerDTO(owner));
        }

        return result;
    }

    @Override
    public List<OwnerDTO> findOwnersByName(String name) {
        List<Owner> resultOwners = daoOwner.findOwnersByName(name);
        List<OwnerDTO> result = new ArrayList<>();

        for (Owner owner: resultOwners){
            result.add(new OwnerDTO(owner));
        }

        return result;
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        User1 user = daoUser.findUserByUsername(username);
        if (user == null){
            return null;
        }else {
            return new UserDTO(user);
        }
    }

    @Override
    public String saveUser(UserDTO user) {
        User1 old_user = daoUser.findUserByUsername(user.getUsername());
        Role newRole = daoRole.findRoleByName(user.getRole().getName());

        if (newRole == null){
            throw new NoSuchElementException("This role not exist");
        }

        if (old_user != null){
            if (!Objects.equals(user.getUsername(), user.getOwner().getId().toString())){
                throw new InvalidParameterException("Username must match with owner ID");
            }
            old_user.setPassword(user.getPassword());
            old_user.setRole(newRole);
            changeOwner(user.getOwner());
            daoUser.save(old_user);
            return old_user.getUsername();
        }else {
            Owner newOwner = new Owner(user.getOwner());
            newOwner = daoOwner.save(newOwner);
            User1 newUser = new User1(user.getPassword(), newOwner, newRole);
            daoUser.save(newUser);
            return newUser.getUsername();
        }
    }

    @Override
    public void deleteUser(Long ownerId) {
        daoOwner.deleteById(ownerId);
        daoUser.deleteByUsername(ownerId.toString());
    }
    @Override
    public void addCat(Long catId, Long ownerId){
        Optional<Owner> owner = daoOwner.findById(ownerId);
        if (owner.isPresent()){
            owner.get().addCat(catId);
            daoOwner.save(owner.get());
        } else {
            throw new NoSuchElementException("User not found");
        }
    }
    @Override
    public void deleteCat(Long catId, Long ownerId){
        Optional<Owner> owner = daoOwner.findById(ownerId);
        if (owner.isPresent()){
            owner.get().removeCat(catId);
            daoOwner.save(owner.get());
        } else {
            throw new NoSuchElementException("User not found");
        }
    }
}
