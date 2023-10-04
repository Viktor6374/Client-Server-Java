package com.example.externalinterfaces.Controllers;
import com.example.externalinterfaces.Entity.Cat;
import com.example.externalinterfaces.Services.CatService;
import com.example.externalinterfaces.Services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/cat")
public class CatController {
    @Autowired
    private CatService catService;
    @Autowired
    OwnerService ownerService;
    @PostFilter("hasRole('ADMIN') or filterObject.ownerID == authentication.principal.owner.id")
    @GetMapping
    public List<Cat> getAll() throws Exception {
        List<Cat> result = catService.getAllCats();
        return result;
    }

    @PostFilter("hasRole('ADMIN') or filterObject.ownerID == authentication.principal.owner.id")
    @GetMapping("/getById")
    public List<Cat> getById(@RequestParam Long catId) throws Exception {
        return Collections.singletonList(catService.findCatByID(catId));
    }

    @PostFilter("hasRole('ADMIN') or filterObject.ownerID == authentication.principal.owner.id")
    @GetMapping("/getByBreed")
    public List<Cat> getCatsByBreed(@RequestParam String breed) throws Exception {
        return catService.findCatByBreed(breed);
    }

    @PostMapping("/save")
    public Long saveCat(@RequestBody Cat cat) throws Exception {
        Long catId = catService.saveCat(cat);
        ownerService.addCat(cat.getOwnerID(), catId);
        return catId;
    }

    @DeleteMapping("/deleteById")
    public void deleteCat(@RequestParam Long catId) throws Exception {
        ownerService.deleteCat(catService.findCatByID(catId).getOwnerID(), catId);
        catService.deleteCat(catId);
    }

    @PostMapping("/addFriendship")
    public void addFriendship(@RequestParam Long catId1, @RequestParam Long catId2) throws Exception {
        catService.addFriendship(catId1, catId2);
    }
}

