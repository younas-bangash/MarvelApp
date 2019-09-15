package com.marvelapp.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DetailsListsItemTest {
    private DetailsListsItem detailsListsItem;


    @Before
    public void setUp() {
        detailsListsItem = new DetailsListsItem();
    }

    @After
    public void tearDown() {
        detailsListsItem = null;
    }

    @Test
    public void name() {
        detailsListsItem.setName("name");

        Assert.assertEquals("name", detailsListsItem.getName());
    }

    @Test
    public void getCollectionURI() {
        detailsListsItem.setCollectionURI("uri");

        Assert.assertEquals("uri", detailsListsItem.getCollectionURI());
    }
}