package com.marvelapp.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.appbar.AppBarLayout;
import com.marvelapp.BR;
import com.marvelapp.R;
import com.marvelapp.api.Resource;
import com.marvelapp.databinding.FragmentCharacterDetailBinding;
import com.marvelapp.model.MarvelCharacter;
import com.marvelapp.ui.base.BaseFragment;
import com.marvelapp.viewmodel.CharacterDetailViewModel;

/**
 * Fragment for displaying the details of each character
 */
public class CharacterDetailFragment extends BaseFragment<CharacterDetailViewModel> {
    private FragmentCharacterDetailBinding fragmentCharacterDetailBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentCharacterDetailBinding = (FragmentCharacterDetailBinding) dataBinding;
        setSupportedActionBar(fragmentCharacterDetailBinding.toolbar);
        initCollapsingToolbar();
        fragmentCharacterDetailBinding.navigateBack.setOnClickListener(view -> getNavController().navigate(R.id.action_characterDetailFragment_to_characterListFragment));
        return fragmentCharacterDetailBinding.getRoot();
    }

    private void initCollapsingToolbar() {
        AppBarLayout mAppBarLayout = fragmentCharacterDetailBinding.appBar;
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    fragmentCharacterDetailBinding.toolbarLayout.setTitle(viewModel.marvelCharacter.getValue().getName());
                } else if (isShow) {
                    isShow = false;
                    fragmentCharacterDetailBinding.toolbarLayout.setTitle("");
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (fragmentSharedViewModel != null) {
            viewModel.marvelCharacter.setValue((MarvelCharacter) fragmentSharedViewModel.getModel(MarvelCharacter.class));
            fragmentCharacterDetailBinding.toolbar.setTitleTextColor(getContext().getResources().getColor(android.R.color.white));
            loadCharacterHeaderImage();
            initRecyclerViews();
        }
    }

    private void initRecyclerViews() {
        fragmentCharacterDetailBinding.comicsListView.setAdapter(viewModel.comicListAdapter);
        ViewCompat.setNestedScrollingEnabled(fragmentCharacterDetailBinding.comicsListView, false);
        fragmentCharacterDetailBinding.setResourceComicsList(Resource.success(viewModel.marvelCharacter.getValue().getComics().getItems()));

        fragmentCharacterDetailBinding.storiesListView.setAdapter(viewModel.storiesListAdapter);
        ViewCompat.setNestedScrollingEnabled(fragmentCharacterDetailBinding.storiesListView, false);
        fragmentCharacterDetailBinding.setResourceStoriesList(Resource.success(viewModel.marvelCharacter.getValue().getStories().getItems()));

        fragmentCharacterDetailBinding.eventListView.setAdapter(viewModel.eventsListAdapter);
        ViewCompat.setNestedScrollingEnabled(fragmentCharacterDetailBinding.eventListView, false);
        fragmentCharacterDetailBinding.setResourceEventList(Resource.success(viewModel.marvelCharacter.getValue().getEvents().getItems()));


    }

    private void loadCharacterHeaderImage() {
        Glide.with(fragmentCharacterDetailBinding.characterImage.getContext())
                .load(viewModel.marvelCharacter.getValue().getThumbnail().getPath() + "." +
                        viewModel.marvelCharacter.getValue().getThumbnail().getExtension())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        fragmentCharacterDetailBinding.imageLoadingHeader.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        fragmentCharacterDetailBinding.imageLoadingHeader.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(fragmentCharacterDetailBinding.characterImage);
    }

    @Override
    protected Class<CharacterDetailViewModel> getViewModel() {
        return CharacterDetailViewModel.class;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_character_detail;
    }
}
