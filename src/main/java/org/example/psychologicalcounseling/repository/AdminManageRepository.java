package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.AdminManage;
import org.example.psychologicalcounseling.model.AdminManagePrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminManageRepository extends JpaRepository<AdminManage, AdminManagePrimaryKey> {
    @Query(nativeQuery = true, value = "SELECT counsellorID FROM AdminManage WHERE adminID = ?1")
    List<Long> findCounsellorIDByAdminID(Long adminID);
}
