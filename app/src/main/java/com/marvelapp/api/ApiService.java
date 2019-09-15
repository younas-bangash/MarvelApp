package com.marvelapp.api;

import com.marvelapp.api.response.CharacterSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * The APIService interface which will contain the semantics of all the REST calls.
 */
public interface ApiService {
    @GET("v1/public/characters")
    Call<CharacterSearchResponse> loadCharacters(@Query("limit") int limit,
                                                 @Query("offset") int offset,
                                                 @Query("nameStartsWith") String nameStartsWith);

    @GET
    Call<CharacterSearchResponse> loadComics(@Url String url);
}
