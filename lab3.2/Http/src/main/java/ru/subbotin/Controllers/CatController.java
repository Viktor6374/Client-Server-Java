package ru.subbotin.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.subbotin.DTO.CatDTO;
import ru.subbotin.DTO.OwnerDTO;
import ru.subbotin.service.CatsServiceImpl;
import java.util.List;

@RestController
@RequestMapping(value = "/cat")
public class CatController {
    @Autowired
    private CatsServiceImpl service;

    @GetMapping
    public List<CatDTO> getAll() {
        return service.getAllCats();
    }

    @GetMapping("/getById")
    public CatDTO getById(@RequestParam int catId) {
        return service.findCatByID(catId);
    }

    @GetMapping("/getByBreed")
    public List<CatDTO> getCatsByBreed(@RequestParam String breed) {
        return service.findCatByBreed(breed);
    }

    @PostMapping("/save")
    public CatDTO saveCat(@RequestBody CatDTO catDTO) throws Exception {
        service.saveCat(catDTO);
        return service.findCatByID(catDTO.getId());
    }

    @DeleteMapping("/deleteById")
    public void deleteCat(@RequestParam int catId) {
        service.deleteCat(catId);
    }

    @PostMapping("/addFriendship")
    public void addFriendship(@RequestParam int catId1, @RequestParam int catId2){
        service.addFriendship(catId1, catId2);
    }
}
