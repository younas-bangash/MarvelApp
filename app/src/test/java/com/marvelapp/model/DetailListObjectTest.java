package com.marvelapp.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DetailListObjectTest {
    private DetailListObject detailListObject;

    @Before
    public void setUp() {
        detailListObject = new DetailListObject();
    }

    @After
    public void tearDown() {
        detailListObject = null;
    }

    @Test
    public void available() {
        detailListObject.setAvailable(10);

        Assert.assertEquals(10, detailListObject.getAvailable());
    }

    @Test
    public void returned() {
        detailListObject.setReturned(10);

        Assert.assertEquals(10, detailListObject.getReturned());
    }

    @Test
    public void collectionURI() {
        detailListObject.setCollectionURI("url");

        Assert.assertEquals("url", detailListObject.getCollectionURI());
    }
}