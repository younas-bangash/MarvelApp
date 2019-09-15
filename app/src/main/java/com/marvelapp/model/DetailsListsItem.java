package com.marvelapp.model;

import androidx.room.Entity;

/**
 * Class to hold single comic item
 */
@Entity
public class DetailsListsItem {
    private String name;
    private String resourceURI;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollectionURI() {
        return resourceURI;
    }

    public void setCollectionURI(String collectionURI) {
        this.resourceURI = collectionURI;
    }
}
