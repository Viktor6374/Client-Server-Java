package com.example.externalinterfaces.Controllers;

import com.example.externalinterfaces.Entity.Cat;
import com.example.externalinterfaces.Entity.Owner;
import com.example.externalinterfaces.Entity.Role;
import com.example.externalinterfaces.Entity.User1;
import com.example.externalinterfaces.Services.CatService;
import com.example.externalinterfaces.Services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/owner")
public class OwnerController {
    @Autowired
    private CatService catService;
    @Autowired
    private OwnerService ownerService;

    @GetMapping
    public List<Owner> getAll() throws Exception {
        return ownerService.getAllOwners();
    }

    @GetMapping("/getById")
    public Owner getOwnerById(@RequestParam Long ownerId) throws Exception {
        return ownerService.findOwnerByID(ownerId);
    }

    @GetMapping("/getByName")
    public List<Owner> getOwnersByName(@RequestParam String name) throws Exception {
        return ownerService.findOwnersByName(name);
    }

    @PostMapping("/save")
    public String saveUser(@RequestBody Owner owner, @RequestParam String role, @RequestParam String password) throws Exception {

        return ownerService.saveUser(new User1(0L, password, owner.getId().toString(), owner, new Role(role)));
    }

    @DeleteMapping("/deleteById")
    public void deleteOwner(@RequestParam Long ownerId) throws Exception {
        for (Long catId : catService.findCatByOwnerId(ownerId).stream().map(Cat::getId).collect(Collectors.toList())){
            catService.deleteCat(catId);
        }
        ownerService.deleteUser(ownerId);
    }
}

