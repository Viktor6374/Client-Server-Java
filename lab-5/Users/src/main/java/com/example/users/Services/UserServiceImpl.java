package com.example.users.Services;

import com.example.users.DTO.OwnerDTO;
import com.example.users.DTO.UserDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Override
    public void saveOwner(OwnerDTO owner) {

    }

    @Override
    public void deleteOwner(int ownerID) {

    }

    @Override
    public OwnerDTO findOwnerByID(Long id) {
        return null;
    }

    @Override
    public ArrayList<OwnerDTO> getAllOwners() {
        return null;
    }

    @Override
    public List<OwnerDTO> findOwnersByName(String name) {
        return null;
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        return null;
    }

    @Override
    public void saveUser(UserDTO user) {

    }

    @Override
    public void deleteUser(Long ownerId) {

    }
}
