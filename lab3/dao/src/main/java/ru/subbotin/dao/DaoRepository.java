package ru.subbotin.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.subbotin.entity.Cat;
import ru.subbotin.entity.Owner;

@Repository
public interface DaoRepository<T> extends JpaRepository<T, Integer> {
}
