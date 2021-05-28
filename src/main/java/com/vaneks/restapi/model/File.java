package com.vaneks.restapi.model;

import com.google.gson.annotations.Expose;
import lombok.*;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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

    @OneToMany(mappedBy = "file", fetch = FetchType.EAGER)
    @Expose(serialize = false)
    private Set<Event> events;

    public File(String fileName, Date date, FileStatus fileStatus) {
        this.fileName = fileName;
        this.date = date;
        this.fileStatus = fileStatus;
    }
}
