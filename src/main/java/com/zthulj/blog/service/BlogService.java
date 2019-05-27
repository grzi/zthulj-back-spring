package com.zthulj.blog.service;

import com.zthulj.blog.dto.Article;
import com.zthulj.blog.dto.Card;
import com.zthulj.blog.exception.BlogException;
import com.zthulj.blog.repository.BlogRepository;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    ArticleValidator validator;

    @Autowired
    MardownService mdService;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    TwitterService twitterService;

    FastDateFormat fastDateFormat = FastDateFormat.getInstance("dd/MM/yyyy");

    public Article getArticleByLink(String link) {
        Article a = blogRepository.findByLinkIgnoreCaseAndPublished(link, true);
        if (a != null) {
            a.getValue().setContentHtml(mdService.convertMardownToHtml(a.getValue().getContentMD()));
            a.getValue().setContentMD(null); // Set to null to improve network data load
            a.setFormattedDate(fastDateFormat.format(a.getPublishDate()));
            a.setPublishDate(null); // Set to null to improve network data load
        }
        return a;
    }

    public Article getFullArticleByLink(String link) {
        return blogRepository.findByLink(link);
    }

    public Article saveArticle(Article article) throws BlogException {

        validator.Validate(article);

        // Permet la génération d'un nouvel id (ne marche pas si on reçoit un id 'vide'
        article.setId(article.getId() != null && article.getId().isEmpty() ? null : article.getId());
        if (null == article.getId()
                && getFullArticleByLink(article.getLink()) != null) {
            throw new BlogException("Article with this link already exist");
        }

        if (article.getPublishDate() == null && article.isPublished()) {
            article.setPublishDate(Calendar.getInstance().getTime());
            twitterService.sendTweet(article);
        }

        return blogRepository.save(article);
    }

    public Page<Card> search(String keywords, int page) {
        PageRequest pRequest = PageRequest.of(page - 1, 4); // Pages starts at 0, on front I want to get 1
        List<Article> articles = blogRepository.findPublishedByKeyword(keywords, pRequest);
        articles.sort(Comparator.comparing(Article::getPublishDate).reversed());
        int totalCount = blogRepository.countArticlePublishedByKeyword(keywords);

        return getCards(pRequest, articles, totalCount);
    }

    public Page<Card> searchAdmin(String keywords, int page) {
        PageRequest pRequest = PageRequest.of(page - 1, 4); // Pages starts at 0, on front I want to get 1

        List<Article> articles = blogRepository.findByKeyword(keywords, pRequest);
        articles.sort(Comparator.comparing(Article::getPublishDate).reversed());

        int totalCount = blogRepository.countByKeyword(keywords);

        return getCards(pRequest, articles, totalCount);
    }

    private Page<Card> getCards(PageRequest pRequest, List<Article> articles, int totalCount) {
        List<Card> cards = new ArrayList<>();
        articles.forEach(e -> cards.add(cardFromArticle(e)));

        Page<Card> resultPage = new PageImpl<>(cards, pRequest, totalCount);
        return resultPage;
    }

    public Collection<Card> listAllPublished() {
        List<Card> cards = new ArrayList<>();
        List<Article> articles = blogRepository.findByPublished(true);
        articles.sort(Comparator.comparing(Article::getPublishDate).reversed());
        articles.forEach(e -> cards.add(cardFromArticle(e)));
        return cards;
    }

    public Collection<Card> listAll() {
        List<Card> cards = new ArrayList<>();
        List<Article> articles = blogRepository.findAll();
        articles.forEach(e -> cards.add(cardFromArticle(e)));
        return cards;
    }

    public Collection<Card> listByCategory(String cat) {
        List<Card> cards = new ArrayList<>();
        List<Article> articles = blogRepository.findByCategoryAndPublished(cat, true);
        articles.sort(Comparator.comparing(Article::getPublishDate).reversed());
        articles.forEach(e -> cards.add(cardFromArticle(e)));
        return cards;
    }

    private Card cardFromArticle(Article e) {
        Card c = new Card();
        c.setCategory(e.getCategory());
        c.setDescription(e.getDescription());
        c.setPublishDate(e.getPublishDate() != null ? fastDateFormat.format(e.getPublishDate()) : "");
        c.setLink(e.getLink());
        c.setTitle(e.getTitle());
        c.setImageCard(e.getImageCard());
        c.setPublished(e.isPublished());
        c.setAuthor(e.getAuthor());
        return c;
    }
}
