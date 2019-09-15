package com.marvelapp.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.marvelapp.model.MarvelCharacter;

/**
 * Database class for the Characters
 */
@Database(entities = {MarvelCharacter.class}, version = 2)
public abstract class CharactersDatabase extends RoomDatabase {
    public abstract CharacterDao characterDao();
}
