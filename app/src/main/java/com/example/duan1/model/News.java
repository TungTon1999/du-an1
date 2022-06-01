package com.example.duan1.model;

public class News {
    private String content;
    private int image;
    private String url;

    public News(String content, int image) {
        this.content = content;
        this.image = image;
    }

    public News(String content, int image, String url) {
        this.content = content;
        this.image = image;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
