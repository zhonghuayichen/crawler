package com.crawler.se.sougou.domin.json;

import java.io.Serializable;

/**
 * Created by zxz on 2016/1/11.
 */
public class SearchEngineSouGou implements Serializable{

    private static final long serialVersionUID = 1L;

    private String title;

    private String urlLink;

    private String content;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
