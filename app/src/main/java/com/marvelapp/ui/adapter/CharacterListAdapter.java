package com.marvelapp.ui.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.marvelapp.api.Resource;
import com.marvelapp.databinding.CharacterListItemBinding;
import com.marvelapp.databinding.ItemDataLoadingBinding;
import com.marvelapp.model.MarvelCharacter;
import com.marvelapp.ui.base.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * List adapter for character list
 */
public class CharacterListAdapter extends BaseAdapter<RecyclerView.ViewHolder, MarvelCharacter> {
    public final MutableLiveData<MarvelCharacter> itemCallListener = new MutableLiveData<>();
    public static final int TYPE_PROGRESS = 0;
    private static final int TYPE_ITEM = 1;
    private List<MarvelCharacter> marvelCharacterList = new ArrayList<>();
    private boolean isLoadingAdded = false;

    public CharacterListAdapter() {
        // Empty constructor
    }

    @Override
    public int getItemViewType(int position) {
        return (position == marvelCharacterList.size() - 1 && isLoadingAdded) ? TYPE_PROGRESS : TYPE_ITEM;
    }


    @Override
    public int getItemCount() {
        return marvelCharacterList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        if (viewType == TYPE_PROGRESS) {
            ItemDataLoadingBinding dataLoadingBinding = ItemDataLoadingBinding.inflate(layoutInflater, parent, false);
            return new DataLoadingItemViewHolder(dataLoadingBinding);

        } else {
            CharacterListItemBinding itemBinding = CharacterListItemBinding.inflate(layoutInflater, parent, false);
            return new CharacterItemViewHolder(itemBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CharacterItemViewHolder) {
            CharacterItemViewHolder characterItemViewHolder = ((CharacterItemViewHolder) holder);
            MarvelCharacter marvelCharacter = marvelCharacterList.get(position);
            characterItemViewHolder.bindView(marvelCharacter);
            loadCharacterImage(characterItemViewHolder.imageView, characterItemViewHolder.progressBar, marvelCharacter);
        }
    }

    public void loadCharacterImage(@NonNull ImageView imageView, @NonNull ProgressBar progressBar, @NonNull MarvelCharacter marvelCharacter) {
        if (marvelCharacter.getThumbnail() == null) {
            return;
        }

        Glide.with(imageView.getContext())
                .load(marvelCharacter.getThumbnail().getPath() + "." + marvelCharacter.getThumbnail().getExtension())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }

    @Override
    public void setData(List<MarvelCharacter> entities) {
        this.marvelCharacterList = entities;
        notifyDataSetChanged();
    }

    public class DataLoadingItemViewHolder extends RecyclerView.ViewHolder {
        public DataLoadingItemViewHolder(ItemDataLoadingBinding binding) {
            super(binding.getRoot());
        }
    }

    public class CharacterItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CharacterListItemBinding binding;
        public ImageView imageView;
        public ProgressBar progressBar;

        public CharacterItemViewHolder(CharacterListItemBinding binding) {
            super(binding.getRoot());
            itemView.setOnClickListener(this);
            this.imageView = binding.characterImage;
            progressBar = binding.imageLoading.progressBar;
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

    public void addLoadingFooter() {
        isLoadingAdded = true;
        marvelCharacterList.add(new MarvelCharacter());
        notifyItemInserted(marvelCharacterList.size() - 1);
        notifyDataSetChanged();
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        if (marvelCharacterList == null || marvelCharacterList.isEmpty()) {
            return;
        }

        int position = marvelCharacterList.size() - 1;
        MarvelCharacter result = marvelCharacterList.get(position);
        if (result != null) {
            marvelCharacterList.remove(position);
            notifyItemRemoved(position);
        }
        notifyDataSetChanged();
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter(value = "resource")
    public static void setResource(RecyclerView recyclerView, Resource resource) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null)
            return;

        if (resource == null || resource.data == null)
            return;

        if (adapter instanceof BaseAdapter) {
            List list = (List) resource.data;
            if(!list.isEmpty()){
                ((BaseAdapter) adapter).dataLoaded.setValue(true);
            }
            ((BaseAdapter) adapter).setData((List) resource.data);
        }
    }

    public List<MarvelCharacter> getMarvelCharacterList() {
        return marvelCharacterList;
    }
}
