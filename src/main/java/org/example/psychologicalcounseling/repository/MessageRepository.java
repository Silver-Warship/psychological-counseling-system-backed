package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
