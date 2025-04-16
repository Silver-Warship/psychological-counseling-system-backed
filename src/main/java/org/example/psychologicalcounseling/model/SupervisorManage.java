package org.example.psychologicalcounseling.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(SupervisorManagePrimaryKey.class)
@Table(name = "supervisorManage")
public class SupervisorManage {
    @Id
    @Column(name = "supervisorID")
    private String supervisorID;

    @Id
    @Column(name = "counsellorID")
    private String counsellorID;
}
