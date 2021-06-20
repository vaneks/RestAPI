package com.vaneks.restapi.model;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @PrimaryKeyJoinColumn
    @Expose
    private long id;

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private long age;

    @Enumerated(EnumType.STRING)
    @Expose
    private AccountStatus accountStatus;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "account", cascade=CascadeType.ALL)
    @Expose(serialize = false)
    private User user;

    public Account(String firstName, String lastName, int age, AccountStatus accountStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.accountStatus = accountStatus;
    }
}
