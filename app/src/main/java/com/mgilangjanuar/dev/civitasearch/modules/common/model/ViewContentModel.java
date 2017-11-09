package com.mgilangjanuar.dev.civitasearch.modules.common.model;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public class ViewContentModel {

    private String title;

    private String content;

    private Object additional;

    public ViewContentModel(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public ViewContentModel(String title, String content, Object additional) {
        this.title = title;
        this.content = content;
        this.additional = additional;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Object getAdditional() {
        return additional;
    }
}
