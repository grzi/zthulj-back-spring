package com.zthulj.blog.repository;

import com.zthulj.blog.dto.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
    User findByUsername(String username);
}
