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

    @Column
    private String fileName;

    @Column
    private Date date;

    @Column
    @Enumerated(EnumType.STRING)
    private FileStatus fileStatus;


    public File(String fileName, Date date, FileStatus fileStatus) {
        this.fileName = fileName;
        this.date = date;
        this.fileStatus = fileStatus;
    }
}
