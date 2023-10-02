package com.example.users.Services;

import com.example.users.DTO.OwnerDTO;
import com.example.users.DTO.UserDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface UserService {
    public void saveOwner(OwnerDTO owner);
    public void deleteOwner(int ownerID);
    public OwnerDTO findOwnerByID(Long id);
    public ArrayList<OwnerDTO> getAllOwners();
    public List<OwnerDTO> findOwnersByName(String name);
    public UserDTO findUserByUsername(String username);
    public void saveUser(UserDTO user);
    public void deleteUser(Long ownerId);

}
