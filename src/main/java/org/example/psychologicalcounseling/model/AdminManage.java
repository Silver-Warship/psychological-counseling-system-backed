package org.example.psychologicalcounseling.model;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "AdminManage")
public class AdminManage {
    @Column(name = "adminID")
    private String adminID;

    @Column(name = "consultantID")
    private String consultantID;
}
