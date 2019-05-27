package com.zthulj.blog.repository;

import com.zthulj.blog.dto.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BlogRepository extends MongoRepository<Article,String> {
    Article findByLinkIgnoreCaseAndPublished(String link, boolean published);

    Article findByLink(String link);

    List<Article> findByPublished(boolean published);

    @Query("{ $and: [ { published: true }, { $or: [ { 'value.contentMD': {$regex : '.*?0.*', $options: 'i'} }, { title: {$regex : '.*?0.*', $options: 'i'} } ] } ] }")
    List<Article> findPublishedByKeyword(String keyword, Pageable pageable);

    @Query(value = "{ $and: [ { published: true }, { $or: [ { 'value.contentMD': {$regex : '.*?0.*', $options: 'i'} }, { title: {$regex : '.*?0.*', $options: 'i'} } ] } ] }", count=true)
    int countArticlePublishedByKeyword(String keyword);

    List<Article> findByCategoryAndPublished(String category, boolean published);

    @Query("{ $or: [ { 'value.contentMD': {$regex : '.*?0.*', $options: 'i'} }, { title: {$regex : '.*?0.*', $options: 'i'} } ] }")
    List<Article> findByKeyword(String keyword, Pageable pageable);

    @Query(value= "{ $or: [ { 'value.contentMD': {$regex : '.*?0.*', $options: 'i'} }, { title: {$regex : '.*?0.*', $options: 'i'} } ] }", count = true)
    int countByKeyword(String keyword);

}
