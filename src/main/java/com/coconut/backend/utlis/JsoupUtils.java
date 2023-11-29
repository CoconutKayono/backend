package com.coconut.backend.utlis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class JsoupUtils {
    /**
     * 将markdown的目录和内容添加外包装。
     * @param html
     */
    public String modifyHtml(String html){
        Document doc = Jsoup.parse(html);

        // 获取目录元素
        Element ulCatalogue = doc.selectFirst(".markdown-catalogue");

        if (ulCatalogue != null){
            //获取目录元素后面的所有元素
            Elements elements = ulCatalogue.nextElementSiblings();
            //创建内容包装
            Element divContent = doc.createElement("div");
            divContent.addClass("markdown-content");

            divContent.appendChildren(elements);

            ulCatalogue.after(divContent);

        } else {
            Elements elements = doc.body().getAllElements();

            Element divContent = doc.createElement("div");
            divContent.addClass("markdown-content");

            divContent.appendChildren(elements);

            doc.body().appendChild(divContent);
        }
        return doc.html();
    }
}
