package com.vn.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "NEWS_TYPE")
public class NewsType {

    @Id
    @Column(name = "NEWS_TYPE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer newsTypeId;


    @Column(name = "DESCRIPTION", length = 10)
    private String description;


    @Column(name = "NEWS_TYPE_NAME", length = 50)
    private String newsTypeName;

    @OneToMany(mappedBy = "newsType")
    private List<News> newList;
}


