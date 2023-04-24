package ru.subbotin.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import ru.subbotin.DTO.CatDTO;
import ru.subbotin.DTO.OwnerDTO;
import ru.subbotin.dao.DaoRepository;
import ru.subbotin.dao.DaoRepositoryImpl;
import ru.subbotin.entity.Cat;
import ru.subbotin.entity.Owner;

import java.util.ArrayList;

@Service
public class CatsServiceImpl implements CatsService{
    @Autowired
    private DaoRepository daoCat;
}