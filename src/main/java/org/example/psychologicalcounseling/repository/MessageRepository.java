package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM Message WHERE sessionID = ?1 order by sendTimestamp")
    List<Message> findMessagesBySessionID(Long sessionID);
}
