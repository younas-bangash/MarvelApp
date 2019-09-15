package com.marvelapp.model.converter;

import androidx.room.TypeConverter;

import com.marvelapp.model.Thumbnail;

/**
 * converter for the thumbnail class to store url into database
 */
public class ThumbnailConverter {

    @TypeConverter
    public Thumbnail imageUrlToThumbnail(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return null;
        }

        String extension = "";

        if (imageUrl.contains(".")) {
            extension = imageUrl.substring(imageUrl.lastIndexOf(".") + 1);
        }

        Thumbnail thumbnail = new Thumbnail();
        thumbnail.setPath(imageUrl.replaceAll("." + extension, ""));
        thumbnail.setExtension(extension);
        return thumbnail;
    }

    @TypeConverter
    public String thumbnailToString(Thumbnail thumbnail) {
        return thumbnail == null ? "" : thumbnail.getPath() + "." + thumbnail.getExtension();
    }
}
