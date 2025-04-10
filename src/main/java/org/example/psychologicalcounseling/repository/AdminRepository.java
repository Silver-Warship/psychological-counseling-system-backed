package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
