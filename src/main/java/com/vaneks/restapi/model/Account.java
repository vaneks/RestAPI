package com.vaneks.restapi.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "age")
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "accountStatus")
    private AccountStatus accountStatus;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "account")
    private User user;
}
