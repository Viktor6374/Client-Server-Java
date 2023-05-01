package ru.subbotin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.subbotin.entity.Owner;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DaoRepositoryOwners extends JpaRepository<Owner, Integer> {
    public List<Owner> findOwnersByName(String name);
}
