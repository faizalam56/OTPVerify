package com.athira.otpverify.codility;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.athira.otpverify.R;
import com.athira.otpverify.databinding.RowItemPhotosBinding;

import java.util.ArrayList;
import java.util.List;

public class RvAdapterPhotos extends RecyclerView.Adapter<RvAdapterPhotos.ViewHolderPhotos> {

    private List<PhotosResponse> photosList = new ArrayList<>();
    @NonNull
    @Override
    public ViewHolderPhotos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        RowItemPhotosBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_item_photos,viewGroup,false);

        return new ViewHolderPhotos(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPhotos viewHolderPhotos, int i) {
        viewHolderPhotos.binding.setPhotosResponse(photosList.get(i));
    }

    @Override
    public int getItemCount() {
        return photosList.size()>0?photosList.size():0;
    }

    public void update(List<PhotosResponse> photosList){
        this.photosList = photosList;
        notifyDataSetChanged();
    }

    class ViewHolderPhotos extends RecyclerView.ViewHolder{
        RowItemPhotosBinding binding;
        public ViewHolderPhotos(@NonNull RowItemPhotosBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
