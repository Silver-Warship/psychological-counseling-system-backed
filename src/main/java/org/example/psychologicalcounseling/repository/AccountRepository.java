package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    // 根据 aid 查询 password
    @Query("SELECT a.password FROM Account a WHERE a.aid = :aid")
    String findPasswordByAid(@Param("aid") int aid);
}
