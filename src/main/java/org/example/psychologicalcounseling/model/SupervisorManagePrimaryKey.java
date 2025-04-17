package org.example.psychologicalcounseling.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SupervisorManagePrimaryKey implements Serializable {
    Long supervisorID;
    Long counsellorID;
}
