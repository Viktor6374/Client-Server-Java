package com.example.cats.Services;

import com.example.cats.DTO.CatDTO;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface CatService {
    public Long saveCat(CatDTO catDTO);
    public void deleteCat(Long catID);
    public CatDTO findCatByID(Long id);
    public ArrayList<CatDTO> getAllCats();
    public void addFriendship(Long idFriend1, Long idFriend2);
    public List<CatDTO> findCatByBreed(String breed);
    public boolean catExist(Long catId);
    public List<CatDTO> findCatByOwnerId(Long ownerId);
}
