package com.marvelapp.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.marvelapp.api.ApiService;
import com.marvelapp.data.CharacterDao;
import com.marvelapp.repository.CharacterListRepository;
import com.marvelapp.util.PaginationScrollListener;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;

public class CharacterListViewModelTest {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    private ApiService apiService;

    @Mock
    private CharacterDao characterDao;

    private CharacterListViewModel characterListViewModel;


    @Before
    public void setUp() {
        characterListViewModel = new CharacterListViewModel(new CharacterListRepository(characterDao, apiService));
    }

    @After
    public void tearDown() {
        characterListViewModel =  null;
    }

    @Test
    public void paginationScrollListener() {
        characterListViewModel.setPaginationScrollListener(new PaginationScrollListener(null) {
            @Override
            protected void loadMoreItems() {
                // Empty Implementation
            }
        });

        Assert.assertNotNull(characterListViewModel.getPaginationScrollListener());
    }

    @Test
    public void characterListLiveData() {

    }

    @Test
    public void characterListAdapter() {
        Assert.assertNotNull(characterListViewModel.getCharacterListAdapter());
    }

    @Test
    public void repository() {
        Assert.assertNotNull(characterListViewModel.getRepository());
    }
}