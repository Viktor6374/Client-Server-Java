package com.example.externalinterfaces.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/owner")
public class OwnerController {
    @Autowired
    private CatsServiceImpl service;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<OwnerDTO> getAll() {
        return service.getAllOwners();
    }

    @GetMapping("/getById")
    public OwnerDTO getOwnerById(@RequestParam int ownerId) {
        return service.findOwnerByID(ownerId);
    }

    @GetMapping("/getByName")
    public List<OwnerDTO> getOwnersByName(@RequestParam String name) {
        return service.findOwnersByName(name);
    }

    @PostMapping("/save")
    @Transactional
    public OwnerDTO saveOwner(@RequestBody OwnerDTO ownerDTO, @RequestParam String role, @RequestParam String password) throws Exception {
        Integer id = service.saveOwner(ownerDTO);
        userService.saveUser(id, role, password);
        return service.findOwnerByID(id);
    }

    @DeleteMapping("/deleteById")
    @Transactional
    public void deleteOwner(@RequestParam int ownerId) {
        userService.deleteUser(ownerId);
        service.deleteOwner(ownerId);
    }
}

