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

    public Integer getAuthor() {
        return authorId;
    }

    public String toTitle(String path) {
        return path.substring(path.lastIndexOf("\\") + 1).replace(".md", "");
    }

    public String toPath(String title) {
        return url + title + ".md";
    }
}
