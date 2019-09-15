package com.marvelapp.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.marvelapp.model.MarvelCharacter;

import java.util.List;


/**
 * Dao class fro reading data from database
 */

@Dao
public interface CharacterDao {

    @Query("SELECT * FROM marvelCharacter")
    LiveData<List<MarvelCharacter>> loadCharacter();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveCharacter(List<MarvelCharacter> marvelCharacterEntities);
}
