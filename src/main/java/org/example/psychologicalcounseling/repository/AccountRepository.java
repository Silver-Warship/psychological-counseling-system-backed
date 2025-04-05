package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
