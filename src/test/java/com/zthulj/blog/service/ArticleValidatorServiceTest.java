package com.zthulj.blog.service;

import com.zthulj.blog.dto.Article;
import com.zthulj.blog.dto.MarkdownContent;
import com.zthulj.blog.exception.BlogException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArticleValidatorServiceTest {

    @InjectMocks
    ArticleValidator articleValidator;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void returnErrorWhenLinkIsNull() {
        Article a = new Article();
        a.setValue(new MarkdownContent());

        Executable codeToTest = () -> {
            articleValidator.Validate(a);
        };

        assertThrows(BlogException.class, codeToTest, "Un champ obligatoire est manquant : link");
    }

    @Test
    public void returnErrorWhenLinkIsEmpty() {
        Article a = new Article();
        a.setLink("");
        a.setValue(new MarkdownContent());

        Executable codeToTest = () -> {
            articleValidator.Validate(a);
        };

        assertThrows(BlogException.class, codeToTest, "Un champ obligatoire est manquant : link");
    }

    @Test
    public void returnErrorWhenValueIsNull() {
        Article a = new Article();
        a.setLink("OK");

        Executable codeToTest = () -> {
            articleValidator.Validate(a);
        };

        assertThrows(BlogException.class, codeToTest, "Un champ obligatoire est manquant : value");
    }

    @Test
    public void returnErrorWhenTitleIsNull() {
        Article a = new Article();
        a.setLink("OK");
        a.setValue(new MarkdownContent());
        a.setTitle("");

        Executable codeToTest = () -> {
            articleValidator.Validate(a);
        };

        assertThrows(BlogException.class, codeToTest, "Un champ obligatoire est manquant : value.title");
    }

    @Test
    public void returnErrorWhenContentIsNull() {
        Article a = new Article();
        a.setLink("OK");
        a.setValue(new MarkdownContent());
        a.setTitle("It's a title");

        Executable codeToTest = () -> {
            articleValidator.Validate(a);
        };

        assertThrows(BlogException.class, codeToTest, "Un champ obligatoire est manquant : value.content");
    }

    @Test
    public void returnErrorWhenContentIsEmpty() {
        Article a = new Article();
        a.setLink("OK");
        a.setValue(new MarkdownContent());
        a.setTitle("It's a title");
        a.getValue().setContentMD("");
        Executable codeToTest = () -> {
            articleValidator.Validate(a);
        };

        assertThrows(BlogException.class, codeToTest, "Un champ obligatoire est manquant : value.content");
    }

    @Test
    public void NoExceptionWhenArticleIsOK() {
        Article a = new Article();
        a.setLink("OK");
        a.setValue(new MarkdownContent());
        a.setTitle("It's a title");
        a.getValue().setContentMD("This is a content");
        Executable codeToTest = () -> {
            articleValidator.Validate(a);
        };
        assertDoesNotThrow(codeToTest);
    }


}
