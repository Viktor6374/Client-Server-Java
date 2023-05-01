package ru.subbotin.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.subbotin.DTO.OwnerDTO;
import ru.subbotin.service.CatsServiceImpl;
import java.util.List;

@RestController
@RequestMapping(value = "/owner")
public class OwnerController {
    @Autowired
    private CatsServiceImpl service;

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
    public OwnerDTO saveOwner(@RequestBody OwnerDTO ownerDTO) {
        service.saveOwner(ownerDTO);
        return service.findOwnerByID(ownerDTO.getId());
    }

    @DeleteMapping("/deleteById")
    public void deleteOwner(@RequestParam int ownerId) {
        service.deleteOwner(ownerId);
    }
}
