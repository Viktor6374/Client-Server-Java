package ru.subbotin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.subbotin.entity.Cat;

import java.util.List;

@Repository
public interface DaoRepositoryCats extends JpaRepository<Cat, Integer> {
    public List<Cat> findCatByBreed(String breed);

}
