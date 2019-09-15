package com.marvelapp.api.response;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ErrorResponseTest {
    private ErrorResponse errorResponse;

    @Before
    public void setUp() {
        errorResponse = new ErrorResponse();
    }

    @After
    public void tearDown() {
        errorResponse = null;
    }

    @Test
    public void getMessage() {
        errorResponse.setMessage("setMessage");

        Assert.assertEquals("setMessage", errorResponse.getMessage());
    }
}