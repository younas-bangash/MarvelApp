package com.marvelapp.ui.adapter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.marvelapp.api.ApiService;
import com.marvelapp.api.response.CharacterSearchResponse;
import com.marvelapp.databinding.CharacterDetailsListItemBinding;
import com.marvelapp.model.DetailsListsItem;
import com.marvelapp.model.Thumbnail;
import com.marvelapp.ui.base.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailListAdapter extends BaseAdapter<RecyclerView.ViewHolder, DetailsListsItem> {
    public final MutableLiveData<DetailsListsItem> itemCallListener = new MutableLiveData<>();
    private List<DetailsListsItem> listData = new ArrayList<>();

    public CharacterDetailListAdapter(ApiService apiService) {
        setApiService(apiService);
    }

    @Override
    public void setData(List<DetailsListsItem> data) {
        this.listData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CharacterDetailsListItemBinding characterDetailsListItemBinding = CharacterDetailsListItemBinding.inflate(layoutInflater, parent, false);
        return new CharacterDetailListAdapter.ItemViewHolder(characterDetailsListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = ((ItemViewHolder) holder);
        DetailsListsItem dataObject = listData.get(position);
        itemViewHolder.name.setText(dataObject.getName());
        loadInternalData(dataObject.getCollectionURI(), itemViewHolder);
    }

    private void loadInternalData(String url, ItemViewHolder itemViewHolder) {
        loadComics(url, new Callback<CharacterSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<CharacterSearchResponse> call,@NonNull Response<CharacterSearchResponse> response) {
                handleSuccessResponse(response, itemViewHolder);
            }

            @Override
            public void onFailure(Call<CharacterSearchResponse> call, Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }

    private void handleSuccessResponse(Response<CharacterSearchResponse> response, ItemViewHolder itemViewHolder) {
        if (response.isSuccessful()) {
            String url;
            if (response.body() != null && response.body().getData() != null
                    && response.body().getData().getResults() != null
                    && response.body().getData().getResults().get(0).getThumbnail() != null) {
                Thumbnail thumbnail = response.body().getData().getResults().get(0).getThumbnail();
                url = thumbnail.getPath() + "." + thumbnail.getExtension();
                loadImage(itemViewHolder, url);
            }
        }
    }

    private void loadImage(ItemViewHolder holder, String url) {
        Glide.with(holder.imageView.getContext())
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CharacterDetailsListItemBinding binding;
        public ImageView imageView;
        public ProgressBar progressBar;
        public TextView name;

        public ItemViewHolder(CharacterDetailsListItemBinding binding) {
            super(binding.getRoot());
            itemView.setOnClickListener(this);
            this.imageView = binding.characterImage;
            progressBar = binding.imageLoading.progressBar;
            name = binding.name;
            this.binding = binding;
        }

        public void bindView(DetailsListsItem item) {
            itemCallListener.setValue(item);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
