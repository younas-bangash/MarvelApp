package com.marvelapp.api;

import com.marvelapp.model.MarvelCharacter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ResourceTest {
    private MarvelCharacter marvelCharacter;

    @Before
    public void setUp() {
        marvelCharacter = new MarvelCharacter();
    }

    @After
    public void tearDown() {
        marvelCharacter = null;
    }

    @Test
    public void success() {
        Resource<MarvelCharacter> successResource = Resource.success(marvelCharacter);

        Assert.assertNotNull(successResource);
    }

    @Test
    public void error() {
        Resource<MarvelCharacter> errorResource = Resource.error("error", marvelCharacter);

        Assert.assertNotNull(errorResource);
        Assert.assertEquals("error", errorResource.getMessage());
    }

    @Test
    public void loading() {
        Resource<MarvelCharacter> successResource = Resource.loading(marvelCharacter);

        Assert.assertNotNull(successResource);
    }
}