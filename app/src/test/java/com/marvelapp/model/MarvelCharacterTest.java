package com.marvelapp.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MarvelCharacterTest {
    private MarvelCharacter marvelCharacter;

    @Before
    public void setUp() {
        marvelCharacter = new MarvelCharacter();
    }

    @After
    public void tearDown() {
        marvelCharacter =  null;
    }

    @Test
    public void id() {
        marvelCharacter.setId(1);

        Assert.assertEquals(1,  marvelCharacter.getId());
    }

    @Test
    public void name() {
        marvelCharacter.setName("name");

        Assert.assertEquals("name", marvelCharacter.getName());
    }

    @Test
    public void description() {
        marvelCharacter.setDescription("setDescription");

        Assert.assertEquals("setDescription", marvelCharacter.getDescription());
    }

    @Test
    public void thumbnail() {
        marvelCharacter.setThumbnail(new Thumbnail());

        Assert.assertNotNull(marvelCharacter.getThumbnail());
    }

    @Test
    public void comics() {
        marvelCharacter.setComics(new Comics());

        Assert.assertNotNull(marvelCharacter.getComics());
    }

    @Test
    public void stories() {
        marvelCharacter.setStories(new Stories());

        Assert.assertNotNull(marvelCharacter.getStories());
    }

    @Test
    public void events() {
        marvelCharacter.setEvents(new Events());

        Assert.assertNotNull(marvelCharacter.getEvents());
    }
}