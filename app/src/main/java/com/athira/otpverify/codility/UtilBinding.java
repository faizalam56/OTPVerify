package com.athira.otpverify.codility;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class UtilBinding {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView,String imageUrl){
        Picasso.with(imageView.getContext())
                .load(imageUrl)
                .into(imageView);
    }
}
