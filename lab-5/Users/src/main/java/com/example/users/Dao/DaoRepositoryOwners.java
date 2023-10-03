package com.example.users.Dao;

import com.example.users.Entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DaoRepositoryOwners extends JpaRepository<Owner, Long> {
    public List<Owner> findOwnersByName(String name);
}
