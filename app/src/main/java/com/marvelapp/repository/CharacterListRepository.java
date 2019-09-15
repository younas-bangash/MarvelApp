package com.marvelapp.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.marvelapp.api.ApiService;
import com.marvelapp.api.NetworkBoundResource;
import com.marvelapp.api.NetworkOnlyResource;
import com.marvelapp.api.Resource;
import com.marvelapp.api.response.CharacterSearchResponse;
import com.marvelapp.data.CharacterDao;
import com.marvelapp.model.MarvelCharacter;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

/**
 * Repository that is used for DoctorFragment
 */
public class CharacterListRepository {
    private int itemLoaded;
    private boolean lastPage;
    private String nameStartsWith;
    private int characterListOffset;
    public static final int CHARACTER_LIST_LIMIT = 20;
    public static final int SEARCH_VIEW_DEBOUNCE_TIMEOUT = 300;
    private final CharacterDao characterDao;
    private final ApiService apiService;

    @Inject
    public CharacterListRepository(CharacterDao dao, ApiService service) {
        this.characterDao = dao;
        this.apiService = service;
    }

    /***
     * search the character list
     * @param offset (optional): The requested offset (number of skipped results) of the call.,
     * @param limit (optional): The requested result limit.,
     * @param nameStartsWith (optional Return characters with names )
     * @return
     */
    public LiveData<Resource<List<MarvelCharacter>>> searchCharacters(int limit, int offset, @Nullable String nameStartsWith) {
        return new NetworkOnlyResource<List<MarvelCharacter>, CharacterSearchResponse>() {
            @NonNull
            @Override
            protected Call<CharacterSearchResponse> createCall() {
                return apiService.loadCharacters(limit, offset, nameStartsWith);
            }

            @Override
            protected void processServiceResponse(CharacterSearchResponse item) {
                processResponse(item);
            }

        }.getAsLiveData();
    }

    private void processResponse(CharacterSearchResponse item){
        itemLoaded = itemLoaded + item.getData().getCount();
        if (item.getData().getTotal() == itemLoaded) {
            characterListOffset = 0;
            setLastPage(true);
        } else if (item.getData().getTotal() < CHARACTER_LIST_LIMIT) {
            characterListOffset = 0;
            setLastPage(true);
        } else {
            characterListOffset = characterListOffset + CHARACTER_LIST_LIMIT;
            setLastPage(false);
        }
    }

    /***
     * Get the character list from db
     * @param offset (optional): The requested offset (number of skipped results) of the call.,
     * @param limit (optional): The requested result limit.,
     * @return
     */
    public LiveData<Resource<List<MarvelCharacter>>> loadCharacters(int limit, int offset) {
        return new NetworkBoundResource<List<MarvelCharacter>, CharacterSearchResponse>() {

            @Override
            protected void processServiceResponse(CharacterSearchResponse item) {
                if (null != item) {
                    characterDao.saveCharacter(item.getData().getResults());
                    processResponse(item);
                }
            }

            @NonNull
            @Override
            protected LiveData<List<MarvelCharacter>> loadFromDb() {
                return characterDao.loadCharacter();
            }

            @NonNull
            @Override
            protected Call<CharacterSearchResponse> createCall() {
                return apiService.loadCharacters(limit, offset, null);
            }

        }.getAsLiveData();
    }

    public String getNameStartsWith() {
        return nameStartsWith;
    }

    public void setNameStartsWith(String nameStartsWith) {
        this.nameStartsWith = nameStartsWith;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public int getCharacterListOffset() {
        return characterListOffset;
    }

    public void setCharacterListOffset(int limit) {
        this.characterListOffset = limit;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }
}
