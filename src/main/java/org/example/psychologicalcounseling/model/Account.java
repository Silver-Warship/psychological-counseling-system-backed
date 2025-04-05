package org.example.psychologicalcounseling.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Account")
public class Account {
    @Id
    @Column(name = "account")
    private Long account;

    @Column(name = "password")
    private String password;
}
