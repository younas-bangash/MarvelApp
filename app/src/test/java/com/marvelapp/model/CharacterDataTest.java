package com.marvelapp.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class CharacterDataTest {
    private CharacterData characterData;

    @Before
    public void setUp() {
        characterData = new CharacterData();
    }

    @After
    public void tearDown() {
        characterData = null;
    }

    @Test
    public void offset() {
        characterData.setOffset("offset");

        Assert.assertEquals("offset", characterData.getOffset());
    }

    @Test
    public void limit() {
        characterData.setLimit("limit");

        Assert.assertEquals("limit", characterData.getLimit());
    }

    @Test
    public void total() {
        characterData.setTotal(10);

        Assert.assertEquals(10, characterData.getTotal());
    }

    @Test
    public void count() {
        characterData.setCount(10);

        Assert.assertEquals(10, characterData.getCount());
    }

    @Test
    public void results() {
        characterData.setResults(new ArrayList<>());

        Assert.assertEquals(0, characterData.getResults().size());
    }
}