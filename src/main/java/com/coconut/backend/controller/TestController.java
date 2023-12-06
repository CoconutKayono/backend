package com.coconut.backend.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping
    public String test() {
        String html = "<ul class='markdown-catalogue'></ul><div class='b'></div>";
        Document doc = Jsoup.parse(html);
        Element ulCatalogue = doc.selectFirst(".markdown-catalogue");
        System.out.println(ulCatalogue);
        return ulCatalogue.html();
    }
}
