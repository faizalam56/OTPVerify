package com.athira.otpverify.codility;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.athira.otpverify.R;
import com.athira.otpverify.databinding.RowAlbumItemBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class RvAlbumAdapter extends RecyclerView.Adapter<RvAlbumAdapter.AlbumViewHolder> {

    private List<AlbumResponse> albumList = new ArrayList<>();
    private AlbumItemClick itemClick;
    public RvAlbumAdapter(AlbumItemClick itemClick){
        this.itemClick = itemClick;
    }
    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        RowAlbumItemBinding rowAlbumItemBinding = DataBindingUtil.inflate(inflater,R.layout.row_album_item,viewGroup,false);
        return new AlbumViewHolder(rowAlbumItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder albumViewHolder, int i) {
        albumViewHolder.rowAlbumItemBinding.setAlbumResponse(albumList.get(i));
    }

    @Override
    public int getItemCount() {
        return albumList.size()>0?albumList.size():0;
    }

    public void update(List<AlbumResponse> albumList){
        this.albumList = albumList;
        notifyDataSetChanged();
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder{
        RowAlbumItemBinding rowAlbumItemBinding;
        public AlbumViewHolder(@NonNull RowAlbumItemBinding rowAlbumItemBinding) {
            super(rowAlbumItemBinding.getRoot());
            this.rowAlbumItemBinding = rowAlbumItemBinding;
            this.rowAlbumItemBinding.setItemClick(itemClick);
        }
    }

    public interface AlbumItemClick{
        void gotoAlbumPhoto(Integer albumId);
    }
}
