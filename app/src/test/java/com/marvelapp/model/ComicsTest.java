package com.marvelapp.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ComicsTest {
    private Comics comics;

    @Before
    public void setUp() {
        comics = new Comics();
    }

    @After
    public void tearDown() {
        comics = null;
    }

    @Test
    public void items() {
        comics.setItems(new ArrayList<>());

        Assert.assertEquals(0, comics.getItems().size());
    }
}