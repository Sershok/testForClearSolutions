package com.example.testforclearsolutions.repository;

import com.example.testforclearsolutions.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);
    void deleteByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.birthDate BETWEEN :fromDate AND :toDate ")
    List<User> findUsersByBirthDateRange(LocalDate fromDate, LocalDate toDate);
}
