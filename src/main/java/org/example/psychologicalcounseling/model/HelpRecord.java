package org.example.psychologicalcounseling.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
