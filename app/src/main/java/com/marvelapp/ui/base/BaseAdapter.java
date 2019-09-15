package com.marvelapp.ui.base;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.marvelapp.api.ApiService;
import com.marvelapp.api.response.CharacterSearchResponse;

import java.util.List;

import retrofit2.Callback;

/***
 * Base class for RecyclerView adapter
 */

public abstract class BaseAdapter<T extends RecyclerView.ViewHolder, D> extends RecyclerView.Adapter<T> {
    public static final String TAG = BaseAdapter.class.getSimpleName();
    private ApiService apiService;

    public final MutableLiveData<Boolean> dataLoaded = new MutableLiveData<>();

    public abstract void setData(List<D> data);

    public void loadComics(String url, Callback<CharacterSearchResponse> callback){
        apiService.loadComics(url).enqueue(callback);
    }

    public void setApiService(ApiService apiService) {
        this.apiService = apiService;
    }

}
