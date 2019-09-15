package com.marvelapp.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ThumbnailTest {
    private Thumbnail thumbnail;

    @Before
    public void setUp() {
        thumbnail = new Thumbnail();
    }

    @After
    public void tearDown() {
        thumbnail =  null;
    }

    @Test
    public void path() {
        thumbnail.setPath("path");

        Assert.assertEquals("path", thumbnail.getPath());
    }

    @Test
    public void extension() {
        thumbnail.setExtension("extension");

        Assert.assertEquals("extension", thumbnail.getExtension());
    }
}