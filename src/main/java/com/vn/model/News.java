package com.vn.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@ToString
@Entity
@Table(name = "NEWS")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NEWS_ID")
    private Integer newsId;


    @Column(name = "CONTENT")
    private String content;


    @Column(name = "PREVIEW")
    private String preview;


    @Column(name = "TITLE")
    private String title;

    @Column(name = "POSTDATE")
    @Temporal(TemporalType.DATE)
    private Date postDate;

    @ManyToOne
    @JoinColumn(name = "NEWS_TYPE_ID", referencedColumnName ="NEWS_TYPE_ID" )
    private NewsType newsType;
}

