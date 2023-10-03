package com.example.users.Services;

import com.example.users.DTO.OwnerDTO;
import com.example.users.DTO.UserDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface UserService {
    public Long changeOwner(OwnerDTO owner);
    public OwnerDTO findOwnerByID(Long id);
    public ArrayList<OwnerDTO> getAllOwners();
    public List<OwnerDTO> findOwnersByName(String name);
    public UserDTO findUserByUsername(String username);
    public String saveUser(UserDTO user);
    public void deleteUser(Long ownerId);
    public void addCat(Long catId, Long ownerId);
    public void deleteCat(Long catId, Long ownerId);
}
