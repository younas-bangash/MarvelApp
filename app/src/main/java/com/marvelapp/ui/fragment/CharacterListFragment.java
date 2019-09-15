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
import com.marvelapp.api.response.ErrorResponse;
import com.marvelapp.databinding.FragmentCharacterListBinding;
import com.marvelapp.model.MarvelCharacter;
import com.marvelapp.ui.base.BaseFragment;
import com.marvelapp.util.PaginationScrollListener;
import com.marvelapp.util.Status;
import com.marvelapp.viewmodel.CharacterListViewModel;

/**
 * Fragment to display list of doctor
 */
public class CharacterListFragment extends BaseFragment<CharacterListViewModel> {
    private FragmentCharacterListBinding listDataBinding;

    @Override
    protected Class<CharacterListViewModel> getViewModel() {
        return CharacterListViewModel.class;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.serviceError.observe(this, this::onErrorResponse);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        observeLiveData();
    }

    private void observeLiveData() {

        viewModel.getCharacterListAdapter().dataLoaded.observe(this, aBoolean -> {
            if(aBoolean){
                listDataBinding.itemDataLoading.progressBar.setVisibility(View.GONE);
                viewModel.getPaginationScrollListener().setLoading(viewModel.getRepository().isLastPage());
                viewModel.getPaginationScrollListener().setLastPage(viewModel.getRepository().isLastPage());
                if (viewModel.getRepository().isLastPage()) {
                    viewModel.getCharacterListAdapter().removeLoadingFooter();
                } else {
                    viewModel.getCharacterListAdapter().addLoadingFooter();
                }
            }
        });

        viewModel.getCharacterListLiveData().observe(this, listResource -> {
            if (null != listResource && (listResource.status == Status.ERROR || listResource.status == Status.SUCCESS)) {
                if (listResource.status == Status.ERROR && listResource.getMessage() != null) {
                    showInfoDialog(listResource.getMessage(), getString(R.string.service_error));
                }
            }

            listDataBinding.setResource(listResource);
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        listDataBinding = ((FragmentCharacterListBinding) dataBinding);
        setSupportedActionBar(listDataBinding.layoutToolbar.appBar);
        listDataBinding.layoutToolbar.appBar.setTitle("");
        initRecyclerView();
        listDataBinding.layoutToolbar.searchIcon.setOnClickListener(view -> getNavController().navigate(R.id.action_doctorListFragment_to_characterSearchingFragment));
        viewModel.getCharacterListAdapter().itemCallListener.observe(this, character -> {
            if (fragmentSharedViewModel != null) {
                fragmentSharedViewModel.setModel(MarvelCharacter.class, character);
            }
            getNavController().navigate(R.id.action_doctorListFragment_to_characterDetailFragment);
        });
        return dataBinding.getRoot();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        listDataBinding.doctorListView.setLayoutManager(layoutManager);
        listDataBinding.doctorListView.setAdapter(viewModel.getCharacterListAdapter());
        viewModel.setPaginationScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                viewModel.getPaginationScrollListener().setLoading(true);
                viewModel.invokeServiceCall.setValue(null);
            }
        });

        listDataBinding.doctorListView.addOnScrollListener(viewModel.getPaginationScrollListener());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_character_list;
    }

    private void onErrorResponse(ErrorResponse serviceError) {
        showInfoDialog(serviceError.getMessage(), serviceError.getMessage());
    }
}
