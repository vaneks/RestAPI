package com.vaneks.restapi.model;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @PrimaryKeyJoinColumn
    @Expose
    private long id;

    @Expose
    private String login;

    @Expose
    private String password;

    @Expose
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    @Expose(serialize = false)
    private Set<Event> event;

    public User(String login, String password, Account account) {
        this.login = login;
        this.password = password;
        this.account = account;
    }
}
