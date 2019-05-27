package com.zthulj.blog.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
public class Card implements Serializable {
    private String title;
    private String description;
    private String publishDate;
    private String category;
    private String link;
    private String imageCard;
    private String author;
    private boolean published;
}
