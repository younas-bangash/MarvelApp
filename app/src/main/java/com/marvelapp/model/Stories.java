package com.marvelapp.model;

import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

import com.marvelapp.model.converter.DetailsListsItemConverter;

import java.util.ArrayList;
import java.util.List;

public class Stories extends DetailListObject {
    @ColumnInfo(name = "stories_list_data")
    @TypeConverters(DetailsListsItemConverter.class)
    private List<DetailsListsItem> items = new ArrayList<>();

    public List<DetailsListsItem> getItems() {
        return items;
    }

    public void setItems(List<DetailsListsItem> items) {
        this.items = items;
    }
}
