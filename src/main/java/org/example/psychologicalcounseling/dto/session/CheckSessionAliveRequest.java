package org.example.psychologicalcounseling.dto.session;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckSessionAliveRequest {
    Long sessionID;
}
