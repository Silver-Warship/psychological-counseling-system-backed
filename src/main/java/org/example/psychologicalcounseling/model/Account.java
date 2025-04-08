package org.example.psychologicalcounseling.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account", nullable = false, length = 11)
    private Long aid;

    @Column(name = "password",nullable = false)
    private String password;

}
