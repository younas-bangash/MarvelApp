package com.marvelapp.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class EventsTest {
    private Events events;

    @Before
    public void setUp()  {
        events = new Events();
    }

    @After
    public void tearDown()  {
        events = null;
    }

    @Test
    public void items() {
        events.setItems(new ArrayList<>());

        Assert.assertEquals(0, events.getItems().size());
    }
}