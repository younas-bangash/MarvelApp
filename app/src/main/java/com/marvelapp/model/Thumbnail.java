package com.marvelapp.model;

/**
 * class contains the images url
 */

public class Thumbnail {
    private String path;
    private String extension;

    public String getPath() {
        return path != null ? path : "";
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

}
