package com.marvelapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.marvelapp.api.Resource;
import com.marvelapp.api.response.ErrorResponse;
import com.marvelapp.model.MarvelCharacter;
import com.marvelapp.repository.CharacterListRepository;
import com.marvelapp.ui.adapter.CharacterListAdapter;
import com.marvelapp.util.PaginationScrollListener;

import java.util.List;

import javax.inject.Inject;

/**
 * ViewModel for the CharacterListFragment
 */

public class CharacterListViewModel extends ViewModel {
    private CharacterListAdapter characterListAdapter = new CharacterListAdapter();
    private LiveData<Resource<List<MarvelCharacter>>> charactersList;
    public final MutableLiveData<ErrorResponse> serviceError = new MutableLiveData<>();
    private PaginationScrollListener paginationScrollListener;
    private CharacterListRepository repository;
    public final MutableLiveData<Void> invokeServiceCall = new MutableLiveData<>();

    @Inject
    public CharacterListViewModel(CharacterListRepository repository) {
        this.repository = repository;
        charactersList = Transformations.switchMap(invokeServiceCall, input ->
                repository.loadCharacters(CharacterListRepository.CHARACTER_LIST_LIMIT, repository.getCharacterListOffset()));
        invokeServiceCall.setValue(null);
    }

    public PaginationScrollListener getPaginationScrollListener() {
        return paginationScrollListener;
    }

    public void setPaginationScrollListener(PaginationScrollListener listener) {
        this.paginationScrollListener = listener;
    }

    public LiveData<Resource<List<MarvelCharacter>>> getCharacterListLiveData() {
        return charactersList;
    }

    public CharacterListAdapter getCharacterListAdapter() {
        return characterListAdapter;
    }

    public CharacterListRepository getRepository() {
        return repository;
    }
}
