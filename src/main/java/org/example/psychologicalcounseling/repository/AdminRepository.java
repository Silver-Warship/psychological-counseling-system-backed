package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM Admin WHERE email = ?1")
    Admin findByEmail(String email);
}
