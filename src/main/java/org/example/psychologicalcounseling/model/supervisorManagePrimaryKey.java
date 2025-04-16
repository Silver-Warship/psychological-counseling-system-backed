package org.example.psychologicalcounseling.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class supervisorManagePrimaryKey implements Serializable {
    Long supervisorID;
    Long counsellorID;
}
