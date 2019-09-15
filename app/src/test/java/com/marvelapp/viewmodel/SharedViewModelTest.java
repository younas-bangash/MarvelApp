package com.marvelapp.viewmodel;

import com.marvelapp.model.ModelBase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SharedViewModelTest {
    private SharedViewModel sharedViewModel;

    @Before
    public void setUp() {
        sharedViewModel = new SharedViewModel();
    }

    @After
    public void tearDown() {
        sharedViewModel = null;
    }

    @Test
    public void model() {
        sharedViewModel.setModel(Character.class, new ModelBase());

        Assert.assertNotNull(sharedViewModel.getModel());
    }

}