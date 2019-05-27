package com.zthulj.blog.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
public class User implements Serializable {
    @Id
    public ObjectId _id;

    public String username;
    public String password;

    public User() {
    }

    public User(ObjectId _id, String username, String password) {
        this._id = _id;
        this.username = username;
        this.password = password;
    }
}
