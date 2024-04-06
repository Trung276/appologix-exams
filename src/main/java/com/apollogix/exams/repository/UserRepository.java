package com.apollogix.exams.repository;


import com.apollogix.exams.entity.User;
import com.apollogix.exams.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndIsActivatedTrue(String email);

    @Query("SELECT u FROM User u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> findByFullNameContainingIgnoreCase(@Param("keyword") String keyword);

    List<User> findByRole(Role role);
}
