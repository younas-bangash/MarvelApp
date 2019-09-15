package com.marvelapp.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class StoriesTest {
    private Stories stories;

    @Before
    public void setUp()  {
        stories =  new Stories();
    }

    @After
    public void tearDown()  {
        stories = null;
    }

    @Test
    public void items() {
        stories.setItems(new ArrayList<>());

        Assert.assertEquals(0, stories.getItems().size());
    }
}