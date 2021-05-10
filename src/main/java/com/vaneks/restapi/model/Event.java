package com.vaneks.restapi.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private Date date;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
