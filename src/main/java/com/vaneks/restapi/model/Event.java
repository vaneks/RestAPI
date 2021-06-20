package com.vaneks.restapi.model;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
    @PrimaryKeyJoinColumn
    @Expose
    private long id;

    @NotFound(action= NotFoundAction.IGNORE)
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    @Expose
    private File file;

    @Expose
    private Date date;

    @Enumerated(EnumType.STRING)
    @Expose
    private EventAction eventAction;

    @NotFound(action=NotFoundAction.IGNORE)
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @Expose
    private User user;


    public Event(File file, Date date, User user, EventAction eventAction) {
        this.file = file;
        this.date = date;
        this.user = user;
        this.eventAction = eventAction;
    }
}
