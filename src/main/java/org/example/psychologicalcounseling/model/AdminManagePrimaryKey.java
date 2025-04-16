package org.example.psychologicalcounseling.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminManagePrimaryKey implements Serializable {
    Long adminID;
    Long counsellorID;
}
