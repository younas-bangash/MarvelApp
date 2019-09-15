package com.marvelapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.marvelapp.api.ApiService;
import com.marvelapp.model.MarvelCharacter;
import com.marvelapp.ui.adapter.CharacterDetailListAdapter;

import javax.inject.Inject;

/**
 *  viewModel class for the {@link com.marvelapp.ui.fragment.CharacterDetailFragment }
 */
public class CharacterDetailViewModel extends ViewModel {

    public final MutableLiveData<MarvelCharacter> marvelCharacter = new MutableLiveData<>();
    public final CharacterDetailListAdapter comicListAdapter;
    public final CharacterDetailListAdapter storiesListAdapter;
    public final CharacterDetailListAdapter eventsListAdapter;

    @Inject
    CharacterDetailViewModel(ApiService apiService){
        comicListAdapter = new CharacterDetailListAdapter(apiService);
        storiesListAdapter = new CharacterDetailListAdapter(apiService);
        eventsListAdapter = new CharacterDetailListAdapter(apiService);
    }
}
