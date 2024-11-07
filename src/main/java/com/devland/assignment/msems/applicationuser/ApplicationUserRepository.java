package com.devland.assignment.msems.applicationuser;

import com.devland.assignment.msems.applicationuser.model.ApplicationUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByUsername(String username);

    Optional<ApplicationUser> findByEmail(String email);

    @Query("SELECT s FROM ApplicationUser s WHERE LOWER(s.fullName) LIKE LOWER(CONCAT('%',?1,'%'))" +
            " AND LOWER(s.username) LIKE LOWER(CONCAT('%',?2,'%'))")
    Page<ApplicationUser> findAll(String name, String username, Pageable pageable);
}