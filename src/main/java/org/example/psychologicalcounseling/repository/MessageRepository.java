package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.Chat.Message;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
