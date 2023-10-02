package com.example.users.Dao;
import com.example.users.Entity.User1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaoRepositoryUsers extends JpaRepository<User1, Long> {
    User1 findUserByUsername(String username);

    void deleteByUsername(String username);
}
