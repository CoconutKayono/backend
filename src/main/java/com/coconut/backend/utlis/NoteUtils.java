package com.coconut.backend.utlis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NoteUtils {
    @Value("${note.url}")
    String url;
    @Value("${note.author}")
    Integer authorId;

    public String getUrl() {
        return url;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public String toTitle(String fileName) {
        return fileName.replace(".md", "");
    }

    public String toPath(String title) {
        return url + title + ".md";
    }
}
