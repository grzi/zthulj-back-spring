package com.zthulj.blog.api;

import com.zthulj.blog.service.MardownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/md")
public class MarkDownApi {

    @Autowired
    MardownService markdownService;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/convertToHtml", method = RequestMethod.POST)
    public String convert(@RequestBody Map<String,String> toConvert) {
        return markdownService.convertMardownToHtml(toConvert.get("text"));
    }
}
