package com.marvelapp.di;

import com.marvelapp.ui.fragment.CharacterDetailFragment;
import com.marvelapp.ui.fragment.CharacterListFragment;
import com.marvelapp.ui.fragment.CharacterSearchingFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * This builder provides android injector service to fragments
 */
@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract CharacterListFragment contributeDoctorListFragment();

    @ContributesAndroidInjector
    abstract CharacterSearchingFragment contributeCharacterSearchingFragment();

    @ContributesAndroidInjector
    abstract CharacterDetailFragment contributeCharacterDetailFragment();
}
