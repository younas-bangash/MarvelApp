package com.marvelapp;

import android.app.Activity;
import android.app.Application;

import com.marvelapp.di.DaggerAppComponent;

import javax.inject.Inject;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Application class for the app
 */
public class MarvelApp extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingInjector;
    private static MarvelApp sInstance;

    public static MarvelApp getAppContext() {
        return sInstance;
    }

    private static synchronized void setInstance(MarvelApp app) {
        sInstance = app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeComponent();
        setInstance(this);
    }

    private void initializeComponent() {
        DaggerAppComponent.builder().application(this).build().inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingInjector;
    }
}
