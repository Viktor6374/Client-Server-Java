package com.example.users.Dao;
import com.example.users.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaoRepositoryRoles extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
}
