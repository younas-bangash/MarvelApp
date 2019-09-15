package com.marvelapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Base Activity of the application
 */
public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    private NavController navController;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentAndroidInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setTitle("");
        DataBindingUtil.setContentView(this, R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentAndroidInjector;
    }

    @Override
    public void onBackPressed() {
        if (R.id.characterListFragment == navController.getCurrentDestination().getId()) {
            finish();
        } else if(R.id.characterDetailFragment == navController.getCurrentDestination().getId()) {
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_characterDetailFragment_to_characterListFragment);
        } else {
            super.onBackPressed();
        }
    }
}

