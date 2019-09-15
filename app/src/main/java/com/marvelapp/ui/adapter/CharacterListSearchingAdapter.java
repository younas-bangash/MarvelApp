package com.marvelapp.ui.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marvelapp.databinding.ItemDataLoadingBinding;
import com.marvelapp.databinding.SearchingCharacterListItemBinding;
import com.marvelapp.model.MarvelCharacter;

/**
 * Adapter for the searching the character
 */
public class CharacterListSearchingAdapter extends CharacterListAdapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        if (viewType == TYPE_PROGRESS) {
            ItemDataLoadingBinding dataLoadingBinding = ItemDataLoadingBinding.inflate(layoutInflater, parent, false);
            return new DataLoadingItemViewHolder(dataLoadingBinding);

        } else {
            SearchingCharacterListItemBinding itemBinding = SearchingCharacterListItemBinding.inflate(layoutInflater, parent, false);
            return new ItemViewHolder(itemBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = ((ItemViewHolder) holder);
            MarvelCharacter doctor = getMarvelCharacterList().get(position);
            itemViewHolder.bindView(doctor);
            loadCharacterImage(itemViewHolder.imageView, itemViewHolder.progressBar, doctor);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private SearchingCharacterListItemBinding binding;
        public ImageView imageView;
        public ProgressBar progressBar;

        public ItemViewHolder(SearchingCharacterListItemBinding binding) {
            super(binding.getRoot());
            this.itemView.setOnClickListener(this);
            this.imageView = binding.characterImage;
            this.progressBar = binding.imageLoading.progressBar;
            this.binding = binding;
        }

        public void bindView(MarvelCharacter item) {
            binding.setItem(item);
        }

        @Override
        public void onClick(View view) {
            itemCallListener.setValue(binding.getItem());
        }
    }
}
