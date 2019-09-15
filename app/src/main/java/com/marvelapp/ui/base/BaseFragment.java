package com.marvelapp.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.marvelapp.R;
import com.marvelapp.viewmodel.SharedViewModel;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Base Fragment Class
 */
public abstract class BaseFragment<V extends ViewModel> extends Fragment {
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    protected SharedViewModel fragmentSharedViewModel;
    private NavController navController;
    protected V viewModel;
    protected ViewDataBinding dataBinding;

    protected abstract Class<V> getViewModel();

    public abstract int getBindingVariable();

    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel());

        if (null != getParentFragment()) {
            fragmentSharedViewModel = getFragmentSharedViewModel(getParentFragment());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        dataBinding.setLifecycleOwner(this);
        dataBinding.setVariable(getBindingVariable(), viewModel);
        dataBinding.executePendingBindings();
        if(null != getActivity()){
            navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        }
        return dataBinding.getRoot();
    }

    public void setSupportedActionBar(@Nullable Toolbar toolbar) {
        if (getActivity() == null) {
            return;
        }
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    public void showInfoDialog(@NonNull String message, @NonNull String title) {
        if (getActivity() == null) {
            return;
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setPositiveButton(getActivity().getString(R.string.ok),
                (dialogInterface, arg1) -> dialogInterface.dismiss());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public NavController getNavController() {
        return navController;
    }

    public static SharedViewModel getFragmentSharedViewModel(Fragment fragment) {
        return ViewModelProviders.of(fragment).get(SharedViewModel.class);
    }
}
