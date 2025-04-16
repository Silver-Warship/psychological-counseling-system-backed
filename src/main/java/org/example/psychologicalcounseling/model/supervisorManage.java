package org.example.psychologicalcounseling.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(supervisorManagePrimaryKey.class)
@Table(name = "supervisorManage")
public class supervisorManage {
    @Id
    @Column(name = "supervisorID")
    private String supervisorID;

    @Id
    @Column(name = "counsellorID")
    private String counsellorID;
}
