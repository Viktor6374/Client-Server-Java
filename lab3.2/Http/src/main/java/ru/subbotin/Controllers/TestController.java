package ru.subbotin.Controllers;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.subbotin.Application;
import ru.subbotin.DTO.CatDTO;
import ru.subbotin.DTO.OwnerDTO;
import ru.subbotin.service.CatsServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {
    @Autowired
    private CatsServiceImpl service;
    @RequestMapping(value="/greeting", method= RequestMethod.GET)
    public ArrayList<OwnerDTO> greetingForm(Model model) {
        return service.getAllOwners();
    }

    @RequestMapping(value = "/greeting", method = RequestMethod.POST)
    public CatDTO greetingSubmit(@RequestBody CatDTO cat, Model model) {
        return cat;
    }
}
