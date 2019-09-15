package com.marvelapp.model;

import androidx.room.Ignore;

/**
 * Class to hol the DetailListObject data
 */
public class DetailListObject {
    @Ignore
    private int available;
    @Ignore
    private int returned;
    @Ignore
    private String collectionURI;

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getReturned() {
        return returned;
    }

    public void setReturned(int returned) {
        this.returned = returned;
    }

    public String getCollectionURI() {
        return collectionURI;
    }

    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

}
