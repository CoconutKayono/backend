package com.coconut.backend.utlis;

import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.misc.Extension;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FlexMarkUtils {

    public String parseMarkdown(String markdown) {
        List<Extension> extensions = new ArrayList<>();
        extensions.add(TocExtension.create());
        extensions.add(TablesExtension.create());

        DataHolder options = new MutableDataSet()
                .set(TocExtension.LIST_CLASS, "markdown-catalogue")
                .set(Parser.EXTENSIONS, extensions);

        Parser parser = Parser.builder(options).build();

        Document document = parser.parse(markdown);

        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        return renderer.render(document);
    }
}
