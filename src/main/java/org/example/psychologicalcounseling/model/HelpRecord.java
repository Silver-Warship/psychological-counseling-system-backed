package org.example.psychologicalcounseling.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "HelpRecord")
public class HelpRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recordID")
    private Long recordID;

    @Column(name = "counsellorID")
    private Long counsellorID;

    @Column(name = "supervisorID")
    private Long supervisorID;

    @Column(name = "userSessionID")
    private Long userSessionID;

    @Column(name = "helpSessionID")
    private Long helpSessionID;
}
