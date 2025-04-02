package org.example.psychologicalcounseling.module.chat.session.checkSessionAlive;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckSessionAliveRequest {
    Long sessionID;
}
