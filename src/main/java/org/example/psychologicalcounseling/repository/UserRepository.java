package org.example.psychologicalcounseling.repository;


import org.example.psychologicalcounseling.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //表格如同实体类User，uid是主键，主键为Long类型
    //继承了JpaRepository，自带对于Users表格的增删改查方法

    User findByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT nickname FROM User WHERE userID in ?1")
    List<String> findAllUserNameByUid(List<Long> uid);
}
