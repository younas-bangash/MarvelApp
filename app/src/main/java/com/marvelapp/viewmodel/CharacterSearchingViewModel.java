package com.marvelapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.marvelapp.api.Resource;
import com.marvelapp.model.MarvelCharacter;
import com.marvelapp.repository.CharacterListRepository;
import com.marvelapp.ui.adapter.CharacterListSearchingAdapter;
import com.marvelapp.util.PaginationScrollListener;
import com.marvelapp.util.RxSearch;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * ViewModel for the Fragment {@link CharacterSearchingViewModel}
 */
public class CharacterSearchingViewModel extends ViewModel {
    private Disposable viewDisposable;
    private LiveData<Resource<List<MarvelCharacter>>> charactersList;
    public final MutableLiveData<Void> invokeServiceCall = new MutableLiveData<>();
    private PaginationScrollListener paginationScrollListener;
    public final CharacterListSearchingAdapter characterListAdapter = new CharacterListSearchingAdapter();
    private CharacterListRepository repository;

    @Inject
    CharacterSearchingViewModel(CharacterListRepository repository) {
        this.repository = repository;
        charactersList = Transformations.switchMap(invokeServiceCall, input -> repository.searchCharacters(
                CharacterListRepository.CHARACTER_LIST_LIMIT, repository.getCharacterListOffset(), repository.getNameStartsWith()));
    }

    public PaginationScrollListener getPaginationScrollListener() {
        return paginationScrollListener;
    }

    public void setPaginationScrollListener(PaginationScrollListener listener) {
        this.paginationScrollListener = listener;
    }

    public Disposable getViewDisposable() {
        return viewDisposable;
    }

    public void setViewDisposable(Disposable viewDisposable) {
        this.viewDisposable = viewDisposable;
    }

    public void initSearchViewWithDebounce(@NonNull SearchView searchView) {
        setViewDisposable(RxSearch.fromSearchView(searchView)
                .debounce(CharacterListRepository.SEARCH_VIEW_DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS)
                .filter(item -> item.length() > 2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> {
                    if (!(query.trim().isEmpty())) {
                        repository.setNameStartsWith(query);
                        invokeServiceCall.setValue(null);
                    }
                }));
    }

    public LiveData<Resource<List<MarvelCharacter>>> getCharacterListLiveData() {
        return charactersList;
    }

    public CharacterListRepository getRepository() {
        return repository;
    }
}
