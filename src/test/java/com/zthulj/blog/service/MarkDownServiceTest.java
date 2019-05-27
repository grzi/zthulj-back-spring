package com.zthulj.blog.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class MarkDownServiceTest {
    @InjectMocks
    MardownService markdownService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void convertMardownToHtml_Test(){
        Assert.assertEquals("<h3>Test</h3>\n", markdownService.convertMardownToHtml("### Test"));
    }
}
