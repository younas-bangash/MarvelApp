package com.marvelapp.api.response;

import com.marvelapp.model.CharacterData;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CharacterSearchResponseTest {
    private CharacterSearchResponse characterSearchResponse;

    @Before
    public void setUp() {
        characterSearchResponse = new CharacterSearchResponse();
    }

    @After
    public void tearDown() {
        characterSearchResponse =  null;
    }

    @Test
    public void code() {
        characterSearchResponse.setCode("Code");

        Assert.assertEquals("Code", characterSearchResponse.getCode());
    }

    @Test
    public void status() {
        characterSearchResponse.setStatus("Status");

        Assert.assertEquals("Status", characterSearchResponse.getStatus());
    }

    @Test
    public void copyright() {
        characterSearchResponse.setCopyright("copyright");

        Assert.assertEquals("copyright", characterSearchResponse.getCopyright());
    }

    @Test
    public void attributionText() {
        characterSearchResponse.setAttributionText("setAttributionText");

        Assert.assertEquals("setAttributionText", characterSearchResponse.getAttributionText());
    }

    @Test
    public void attributionHTML() {
        characterSearchResponse.setAttributionHTML("attributionHTML");

        Assert.assertEquals("attributionHTML", characterSearchResponse.getAttributionHTML());
    }

    @Test
    public void data() {
        CharacterData data = new CharacterData();
        characterSearchResponse.setData(data);

        Assert.assertEquals(data, characterSearchResponse.getData());
    }

    @Test
    public void etag() {
        characterSearchResponse.setEtag("eTag");

        Assert.assertEquals("eTag", characterSearchResponse.getEtag());
    }
}