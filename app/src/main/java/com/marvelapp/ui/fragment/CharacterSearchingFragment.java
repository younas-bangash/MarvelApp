package com.marvelapp.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.marvelapp.BR;
import com.marvelapp.R;
import com.marvelapp.databinding.FragmentCharacterSearchingBinding;
import com.marvelapp.model.MarvelCharacter;
import com.marvelapp.ui.base.BaseFragment;
import com.marvelapp.util.PaginationScrollListener;
import com.marvelapp.util.Status;
import com.marvelapp.viewmodel.CharacterSearchingViewModel;

import java.util.List;

/**
 * Fragment for searching the character
 */
public class CharacterSearchingFragment extends BaseFragment<CharacterSearchingViewModel> {
    private FragmentCharacterSearchingBinding searchingBinding;

    @Override
    protected Class<CharacterSearchingViewModel> getViewModel() {
        return CharacterSearchingViewModel.class;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_character_searching;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        observeLiveData();
        viewModel.characterListAdapter.itemCallListener.observe(this, character -> {
            if (fragmentSharedViewModel != null) {
                fragmentSharedViewModel.setModel(MarvelCharacter.class, character);
            }
            getNavController().navigate(R.id.action_characterSearchingFragment_to_characterDetailFragment);
        });
    }

    private void observeLiveData() {
        viewModel.getCharacterListLiveData().observe(this, listResource -> {
            if (null != listResource && (listResource.status == Status.ERROR || listResource.status == Status.SUCCESS)) {
                searchingBinding.itemDataLoading.progressBar.setVisibility(View.GONE);
                List dataList = (List) listResource.data;

                if (listResource.status == Status.ERROR && listResource.getMessage() != null) {
                    showInfoDialog(listResource.getMessage(), getString(R.string.service_error));
                } else if (dataList.isEmpty()) {
                    showInfoDialog("No Record Found", getString(R.string.service_error));
                }

                viewModel.getPaginationScrollListener().setLoading(viewModel.getRepository().isLastPage());
                viewModel.getPaginationScrollListener().setLastPage(viewModel.getRepository().isLastPage());
                if (viewModel.getRepository().isLastPage()) {
                    viewModel.characterListAdapter.removeLoadingFooter();
                } else {
                    viewModel.characterListAdapter.addLoadingFooter();
                }
            }
            searchingBinding.setResource(listResource);
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        searchingBinding = ((FragmentCharacterSearchingBinding) dataBinding);
        searchingBinding.layoutToolbar.cancelAction.setOnClickListener(view -> getNavController().navigate(R.id.action_characterSearchingFragment_to_doctorListFragment));
        setSupportedActionBar(searchingBinding.layoutToolbar.appBar);
        initRecyclerView();
        viewModel.initSearchViewWithDebounce(searchingBinding.layoutToolbar.editSearchView);
        return dataBinding.getRoot();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        searchingBinding.listView.setLayoutManager(layoutManager);
        searchingBinding.listView.setAdapter(viewModel.characterListAdapter);
        viewModel.setPaginationScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                viewModel.getPaginationScrollListener().setLoading(true);
                viewModel.invokeServiceCall.setValue(null);
            }
        });
        searchingBinding.listView.addOnScrollListener(viewModel.getPaginationScrollListener());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (viewModel.getViewDisposable() != null) {
            viewModel.getViewDisposable().dispose();
        }
    }
}
