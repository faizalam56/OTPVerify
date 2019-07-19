package com.athira.otpverify.codility;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.athira.otpverify.R;
import com.athira.otpverify.databinding.ActivityPhotosBinding;
import com.athira.otpverify.dto.Resource;
import com.athira.otpverify.utils.Globals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhotosActivity extends AppCompatActivity {
    private ActivityPhotosBinding binding;
    private PhotosViewModel viewModel;
    private int albumId;
    private RvAdapterPhotos adapter;
    private Globals mGlobals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.activity_photos,null,false);
        setContentView(binding.getRoot());
        albumId = getIntent().getExtras().getInt("albumId");

        viewModel = ViewModelProviders.of(this).get(PhotosViewModel.class);
        if(albumId!=0)viewModel.callPhotosApi(getIdQueryMap(albumId));
        viewModel.getPhotos().observe(this, new Observer<Resource<PhotosResponse[]>>() {
            @Override
            public void onChanged(@Nullable Resource<PhotosResponse[]> resource) {
                processResponse(resource);
            }
        });

        init();
    }

    private void init(){
        adapter = new RvAdapterPhotos();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.rvPhotos.setLayoutManager(layoutManager);
        binding.rvPhotos.setAdapter(adapter);

        mGlobals = new Globals();
    }

    private void processResponse(Resource resource){
        switch (resource.getStatus()){
            case LOADING:
                binding.setShowProgress(true);
                break;
            case SUCCESS: {
                binding.setShowProgress(false);
                PhotosResponse[] resourceData = (PhotosResponse[]) resource.getData();
                adapter.update(getPhotosList(resourceData));
                break;
            }
            case ERROR:
                binding.setShowProgress(false);
                PhotosResponse[] resourceData = (PhotosResponse[]) resource.getData();
                if(resourceData!=null){
                    mGlobals.showAlertDialog(Integer.toString(resourceData.length),this);
                }else{
                    mGlobals.showAlertDialog("Request Failed Try again....",this);
                }
                break;
        }

    }

    private Map<String,String> getIdQueryMap(Integer albumId){
        Map<String,String> data = new HashMap<>();
        data.put("albumId",albumId.toString());
        return data;
    }

    private List<PhotosResponse> getPhotosList(PhotosResponse[] resourceData){
        List<PhotosResponse> photosList = new ArrayList<>();
        for(PhotosResponse photosResponse:resourceData){
            photosList.add(photosResponse);
        }
        return photosList;
    }
}
