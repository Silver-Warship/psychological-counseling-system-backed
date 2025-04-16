package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.supervisorManage;
import org.example.psychologicalcounseling.model.supervisorManagePrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupervisorManageRepository extends JpaRepository<supervisorManage, supervisorManagePrimaryKey> {
    @Query(nativeQuery = true, value = "SELECT counsellorID FROM AdminManage WHERE supervisorID = ?1")
    List<Long> findCounsellorIDBySupervisorID(Long supervisorID);
}
