package com.example.rohan.parsecom;

import java.util.Date;

/**
 * Created by Justin on 11/2/2015.
 */
public class Post {
    String content, firstLast, userName;
    Date date;

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public String getFirstLast() {
        return firstLast;
    }

    public void setFirstLast(String firstLast) {
        this.firstLast = firstLast;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Post{" +
                "content='" + content + '\'' +
                ", firstLast='" + firstLast + '\'' +
                ", userName='" + userName + '\'' +
                ", date=" + date +
                '}';
    }
}
