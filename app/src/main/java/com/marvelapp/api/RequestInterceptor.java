package com.marvelapp.api;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This okhttp interceptor is responsible for adding the common query parameters and headers
 * for every service calls
 */
@Singleton
public class RequestInterceptor implements Interceptor {
    private static final String TAG = RequestInterceptor.class.getSimpleName();

    private static final String PARAM_KEY_API = "apikey";
    private static final String PARAM_KEY_HASH = "hash";
    private static final String PARAM_KEY_TS = "ts";

    private static final String API_KEY_PUBLIC = "aba6c3ebd845622f228f456297c08a17";
    private static final String API_KEY_PRIVATE = "b88d1b6b00f5768fcfc5b6428b9e1ebeda14aa5c";

    @Inject
    public RequestInterceptor() {
        // Intentionally blank
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        String ts = String.valueOf(Calendar.getInstance().getTimeInMillis());
        HttpUrl url = request
                .url()
                .newBuilder()
                .addQueryParameter(PARAM_KEY_TS, ts)
                .addQueryParameter(PARAM_KEY_API, API_KEY_PUBLIC)
                .addQueryParameter(PARAM_KEY_HASH, getMD5(ts + API_KEY_PRIVATE + API_KEY_PUBLIC))
                .build();

        request = request
                .newBuilder()
                .url(url)
                .addHeader("Accept", "application/json")
                .build();

        return chain.proceed(request);
    }

    public String getMD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            Log.d(TAG, "MD5() called with: md5 = [" + md5 + "]");
        }
        return null;
    }
}