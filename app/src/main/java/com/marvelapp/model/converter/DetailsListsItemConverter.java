package com.marvelapp.model.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.marvelapp.model.DetailsListsItem;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Converter class for comic list
 */
public class DetailsListsItemConverter {
    private static Gson gson = new Gson();
    @TypeConverter
    public static List<DetailsListsItem> stringToList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<DetailsListsItem>>() {}.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String ListToString(List<DetailsListsItem> someObjects) {
        return gson.toJson(someObjects);
    }
}
