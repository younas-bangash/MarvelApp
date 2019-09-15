package com.marvelapp.api;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.google.gson.Gson;
import com.google.gson.stream.MalformedJsonException;
import com.marvelapp.MarvelApp;
import com.marvelapp.R;
import com.marvelapp.api.response.CharacterSearchResponse;
import com.marvelapp.api.response.ErrorResponse;
import com.marvelapp.util.AbsentLiveData;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public abstract class NetworkOnlyResource<T, V> {
    private final MediatorLiveData<Resource<T>> result = new MediatorLiveData<>();

    @MainThread
    protected NetworkOnlyResource() {
        result.setValue(Resource.loading(null));
        fetchFromNetwork(AbsentLiveData.create());
    }


    /**
     * This method fetches the data from remoted service and save it to local db
     *
     * @param liveDataSource - Database source
     */
    private void fetchFromNetwork(final LiveData<T> liveDataSource) {
        result.addSource(liveDataSource, newData -> result.setValue(Resource.loading(newData)));
        createCall().enqueue(new Callback<V>() {
            @Override
            public void onResponse(@NonNull Call<V> call, @NonNull Response<V> response) {
                result.removeSource(liveDataSource);
                if (response.isSuccessful()) {
                    processServiceResponse(response.body());
                    CharacterSearchResponse responseData = (CharacterSearchResponse) response.body();
                    assert responseData != null;
                    @SuppressWarnings("unchecked") T listData = (T) responseData.getData().getResults();
                    result.addSource(liveDataSource, newData -> result.setValue(Resource.success(listData)));
                } else {
                    handleServiceError(response, liveDataSource);
                }
            }

            @Override
            public void onFailure(@NonNull Call<V> call, @NonNull Throwable t) {
                result.removeSource(liveDataSource);
                result.addSource(liveDataSource, newData -> result.setValue(Resource.error(getCustomErrorMessage(t), newData)));
            }
        });
    }

    @NonNull
    @MainThread
    protected abstract Call<V> createCall();

    @WorkerThread
    protected abstract void processServiceResponse(V item);

    public final LiveData<Resource<T>> getAsLiveData() {
        return result;
    }

    private void handleServiceError(@NonNull Response<V> response, final LiveData<T> dbSource) {
        result.removeSource(dbSource);
        try {
            final ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().charStream(), ErrorResponse.class);
            result.setValue(Resource.error(errorResponse.getMessage(), null));
        } catch (Exception ex) {
            result.setValue(Resource.error(getCustomErrorMessage(ex), null));
        }
    }

    private String getCustomErrorMessage(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            return MarvelApp.getAppContext().getString(R.string.requestTimeOutError);
        } else if (error instanceof MalformedJsonException) {
            return MarvelApp.getAppContext().getString(R.string.responseMalformedJson);
        } else if (error instanceof IOException) {
            return MarvelApp.getAppContext().getString(R.string.networkError);
        } else if (error instanceof HttpException) {
            return (((HttpException) error).response().message());
        } else {
            return MarvelApp.getAppContext().getString(R.string.unknownError);
        }
    }
}
