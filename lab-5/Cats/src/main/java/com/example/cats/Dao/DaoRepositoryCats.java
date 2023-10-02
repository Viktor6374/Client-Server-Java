package com.example.cats.Dao;

import com.example.cats.Entity.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DaoRepositoryCats extends JpaRepository<Cat, Long> {
    public List<Cat> findCatByBreed(String breed);
}
