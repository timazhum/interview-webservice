package com.timazhum.interview.viewmodels;

public class PostRequestDto {
    private String url;

    public PostRequestDto() {
    }

    public PostRequestDto(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
