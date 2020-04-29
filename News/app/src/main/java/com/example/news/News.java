package com.example.news;

public class News {

    //global variables
    private String mTitle, mDescription, mAuthor, mUrl, mUrlToImage, mPublishedAt;

    public News(String title, String description, String author, String url, String publishedAt, String urlToImage) {
        this.mTitle = title;
        this.mDescription = description;
        this.mAuthor = author;
        this.mUrl = url;
        this.mPublishedAt = publishedAt;
        this.mUrlToImage = urlToImage;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getDate() {
        return mPublishedAt;
    }

    public String getUrlToImage() {
        return mUrlToImage;
    }
}
