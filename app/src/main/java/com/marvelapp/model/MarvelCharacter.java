package com.marvelapp.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.marvelapp.model.converter.ThumbnailConverter;

/**
 * class contains the character result
 */

@Entity(tableName = "marvelCharacter")
public class MarvelCharacter extends ModelBase {
    @PrimaryKey
    private long id;
    private String name;
    private String description;

    @TypeConverters(ThumbnailConverter.class)
    private Thumbnail thumbnail;

    @Embedded
    private Comics comics;

    @Embedded
    private Stories stories;

    @Embedded
    private Events events;

    public MarvelCharacter() {
        // Empty Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Comics getComics() {
        return comics;
    }

    public void setComics(Comics detailListObject) {
        this.comics = detailListObject;
    }

    public Stories getStories() {
        return stories;
    }

    public void setStories(Stories stories) {
        this.stories = stories;
    }

    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
        this.events = events;
    }

}
