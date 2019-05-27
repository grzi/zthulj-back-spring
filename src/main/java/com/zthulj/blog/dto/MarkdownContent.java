package com.zthulj.blog.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
public class MarkdownContent implements Serializable {
    private String contentMD;
    private String contentHtml;
}
