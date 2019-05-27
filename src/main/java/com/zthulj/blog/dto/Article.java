package com.zthulj.blog.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "blog")
@Getter
@Setter
@EqualsAndHashCode
public class Article implements Serializable {

    @Id
    private String id;
    private String link;
    private String description;
    private String title;
    private String category;
    private String subtitle;
    private Date publishDate;
    private String formattedDate;
    private String author;
    private String imageCard;
    private boolean published;

    private MarkdownContent value;

    public Article() {
    }

    public Article(String link, MarkdownContent val) {
        this.link = link;
        this.value = val;
    }
}
