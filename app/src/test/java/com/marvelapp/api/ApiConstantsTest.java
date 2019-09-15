package com.marvelapp.api;

import org.junit.Assert;
import org.junit.Test;

public class ApiConstantsTest {

    @Test
    public void constantValues(){
        Assert.assertEquals(30000, ApiConstants.CONNECT_TIMEOUT);
        Assert.assertEquals(30000, ApiConstants.READ_TIMEOUT);
        Assert.assertEquals(30000, ApiConstants.WRITE_TIMEOUT);
    }
}