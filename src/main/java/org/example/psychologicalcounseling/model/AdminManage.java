package org.example.psychologicalcounseling.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(AdminManagePrimaryKey.class)
@Table(name = "AdminManage")
public class AdminManage {
    @Id
    @Column(name = "adminID")
    private String adminID;

    @Id
    @Column(name = "counsellorID")
    private String counsellorID;
}
