package com.example.smartstore.repository;

import com.example.smartstore.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUserName(String username);
    Users findAllByUserName(String username);
    Users findAllByUserNameAndPassword(String userName,String password);
    boolean existsAllByUserName(String userName);
    boolean existsAllByEmail(String emile);
}
