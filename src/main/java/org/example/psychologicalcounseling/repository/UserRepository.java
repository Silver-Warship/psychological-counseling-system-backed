package org.example.psychologicalcounseling.repository;


import org.example.psychologicalcounseling.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //表格如同实体类User，uid是主键，主键为Long类型
    //继承了JpaRepository，自带对于Users表格的增删改查方法

    User findByEmail(String email);

}
