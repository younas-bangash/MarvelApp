package com.marvelapp.di;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.marvelapp.viewmodel.CharacterDetailViewModel;
import com.marvelapp.viewmodel.CharacterListViewModel;
import com.marvelapp.viewmodel.CharacterSearchingViewModel;
import com.marvelapp.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Inject dependencies via constructor injection
 */
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CharacterListViewModel.class)
    abstract ViewModel bindsDoctorListViewModel(CharacterListViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CharacterSearchingViewModel.class)
    abstract ViewModel bindsCharacterSearchingViewModel(CharacterSearchingViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailViewModel.class)
    abstract ViewModel bindsCharacterDetailViewModel(CharacterDetailViewModel viewModel);

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory viewModelFactory);
}
