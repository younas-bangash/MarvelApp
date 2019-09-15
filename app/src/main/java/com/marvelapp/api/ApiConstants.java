package com.marvelapp.api;


/**
 * Keep all the service related constants here.
 */
public class ApiConstants {
    public static final String SERVICES_BASE_URL = "https://gateway.marvel.com:443/";
    public static final long CONNECT_TIMEOUT = 30000;
    public static final long READ_TIMEOUT = 30000;
    public static final long WRITE_TIMEOUT = 30000;

    private ApiConstants(){
        // Private constructor to hide the implicit one
    }
}
