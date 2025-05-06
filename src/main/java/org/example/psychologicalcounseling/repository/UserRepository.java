package org.example.psychologicalcounseling.repository;


import org.example.psychologicalcounseling.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT nickname FROM User WHERE userID=?1")
    String findUserNameByUid(Long uid);

    List<User> findByUidOrNicknameContainingIgnoreCase(Long id, String nickname);


}
