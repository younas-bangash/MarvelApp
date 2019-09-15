package com.marvelapp.api.response;

import com.google.gson.annotations.SerializedName;
import com.marvelapp.model.CharacterData;

/**
 * Response class for searching the character
 */
public class CharacterSearchResponse {
    private String code;
    private String status;
    private String copyright;
    private String attributionText;
    private String attributionHTML;
    private CharacterData data;

    @SerializedName("etag")
    private String eTag;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getAttributionText() {
        return attributionText;
    }

    public void setAttributionText(String attributionText) {
        this.attributionText = attributionText;
    }

    public String getAttributionHTML() {
        return attributionHTML;
    }

    public void setAttributionHTML(String attributionHTML) {
        this.attributionHTML = attributionHTML;
    }

    public CharacterData getData() {
        return data;
    }

    public void setData(CharacterData data) {
        this.data = data;
    }

    public String getEtag() {
        return eTag;
    }

    public void setEtag(String eTag) {
        this.eTag = eTag;
    }
}
