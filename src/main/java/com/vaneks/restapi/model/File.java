package com.vaneks.restapi.model;

import com.google.gson.annotations.Expose;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fileName;

    private Date date;

    @Column
    @Enumerated(EnumType.STRING)
    private FileStatus fileStatus;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @Expose
    private User user;

    public File(String fileName, Date date, FileStatus fileStatus, User user) {
        this.fileName = fileName;
        this.date = date;
        this.fileStatus = fileStatus;
        this.user = user;
    }

}
