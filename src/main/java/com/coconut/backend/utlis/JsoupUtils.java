package com.coconut.backend.utlis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class JsoupUtils {
    /**
     * 将markdown的目录和内容添加外包装。
     */
    public String getModifyHtml(String html) {
        Document doc = Jsoup.parse(html);

        // 获取目录元素
        Element ulCatalogue = doc.selectFirst(".markdown-catalogue");

        if (ulCatalogue != null) {
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

    public String getCatalogue(String html){
        Document doc = Jsoup.parse(html);
        Element ulCatalogue = doc.selectFirst(".markdown-catalogue");
        if (ulCatalogue == null){
            return null;
        }else {
            return String.valueOf(ulCatalogue);
        }
    }

    public String getData(String html){
        Document doc = Jsoup.parse(html);
        Element data = doc.selectFirst(".markdown-content");
        if (data == null){
            return null;
        }else {
            return String.valueOf(data);
        }
    }

    public String getFirstImageForPreview(String html) {
        Document doc = Jsoup.parse(html);
        Elements imgElements = doc.select("img");

        if (!imgElements.isEmpty()) {
            Element firstImgElement = imgElements.first();

            return Objects.requireNonNull(firstImgElement).attr("src");
        } else {
            return "No images found on the page.";
        }
    }
}
