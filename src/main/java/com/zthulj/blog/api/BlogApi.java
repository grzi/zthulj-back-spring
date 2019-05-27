package com.zthulj.blog.api;

import com.zthulj.blog.dto.Article;
import com.zthulj.blog.dto.Card;
import com.zthulj.blog.exception.BlogException;
import com.zthulj.blog.referential.SecurityConfig;
import com.zthulj.blog.service.BlogService;
import com.zthulj.blog.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@RestController
@CrossOrigin(origins = "*")
public class BlogApi {

    private Logger logger = LoggerFactory.getLogger(BlogApi.class);

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    BlogService blogService;

    @Autowired
    StorageService storageService;


    @GetMapping(value = SecurityConfig.PUBLIC_API + "/blog/{link}")
    public Article getArticle(@PathVariable("link") String link) {
        return blogService.getArticleByLink(link);
    }


    @GetMapping(value = SecurityConfig.SECURED_API + "/blog/full/{link}")
    public Article getFullArticle(@PathVariable("link") String link) {
        return blogService.getFullArticleByLink(link);
    }


    @GetMapping(value = SecurityConfig.PUBLIC_API + "/blog/search/{keywords}/{page}")
    public Page<Card> search(@PathVariable("keywords") String keywords, @PathVariable("page") int page) {
        return blogService.search(keywords, page);
    }


    @GetMapping(value = SecurityConfig.SECURED_API + "/blog/searchAdmin/{keywords}/{page}")
    public Page<Card> searchAdmin(@PathVariable("keywords") String keywords, @PathVariable("page") int page) {
        return blogService.searchAdmin(keywords, page);
    }


    @GetMapping(value = SecurityConfig.PUBLIC_API + "/blog/list")
    public Collection<Card> list() {
        return blogService.listAllPublished();
    }


    @GetMapping(value = SecurityConfig.SECURED_API + "/blog/listAll")
    public Collection<Card> listDraft(){
        return blogService.listAll();
    }


    @GetMapping(value = SecurityConfig.PUBLIC_API + "/blog/list/{cat}")
    public Collection<Card> list(@PathVariable("cat") String cat) {
        return blogService.listByCategory(cat);
    }


    @PostMapping(value = SecurityConfig.SECURED_API + "/blog/push")
    public ResponseEntity<?> pushArticle(@RequestBody Article article) {
        try {
            return new ResponseEntity<>(blogService.saveArticle(article), HttpStatus.OK);
        } catch (BlogException e) {
            return new ResponseEntity<>("Error while saving the article : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(value = SecurityConfig.SECURED_API + "/blog/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            var result = storageService.storeImage(file);
            return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
