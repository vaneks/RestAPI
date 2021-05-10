package com.vaneks.restapi.model;

import com.google.gson.annotations.Expose;
import lombok.*;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "account", cascade=CascadeType.ALL)
    @Expose
    private User user;

    public Account(String firstName, String lastName, int age, AccountStatus accountStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.accountStatus = accountStatus;
    }
}
