package com.example.remi.ekio;

/**
 * Created by remi on 01/06/16.
 */
public class Collectionable {

    private long id;
    private String title;
    private String date;
    private String location;
    private String comment;
    private String keyWords;
    private String photoPath;

    public Collectionable(long id, String title, String date, String location, String comment, String keyWords, String photoPath) {
        super();
        this.id = id;
        this.title = title;
        this.date = date;
        this.location = location;
        this.comment = comment;
        this.keyWords = keyWords;
        this.photoPath = photoPath;
    }
    public Collectionable(String title, String date, String location, String comment, String keyWords, String photoPath) {
        super();
        this.title = title;
        this.date = date;
        this.location = location;
        this.comment = comment;
        this.keyWords = keyWords;
        this.photoPath = photoPath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getcomment() {
        return comment;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public String getPhotoPath() {
        return photoPath;
    }
}
