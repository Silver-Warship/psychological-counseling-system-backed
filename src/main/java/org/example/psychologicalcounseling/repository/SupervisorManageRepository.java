package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.SupervisorManage;
import org.example.psychologicalcounseling.model.SupervisorManagePrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupervisorManageRepository extends JpaRepository<SupervisorManage, SupervisorManagePrimaryKey> {
    @Query(nativeQuery = true, value = "SELECT counsellorID FROM SupervisorManage WHERE supervisorID = ?1")
    List<Long> findCounsellorIDBySupervisorID(Long supervisorID);
}
