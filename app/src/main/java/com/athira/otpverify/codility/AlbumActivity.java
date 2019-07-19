package com.athira.otpverify.codility;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.athira.otpverify.R;
import com.athira.otpverify.databinding.ActivityAlbumBinding;
import com.athira.otpverify.dto.Resource;
import com.athira.otpverify.utils.Globals;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity implements RvAlbumAdapter.AlbumItemClick {
    private ActivityAlbumBinding binding;
    private AlbumViewModel viewModel;
    private Globals mGlobals;
    private RvAlbumAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.activity_album,null,false);
        setContentView(binding.getRoot());

        viewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);
        viewModel.callAlbumApi();
        viewModel.getAlbum().observe(this, new Observer<Resource<AlbumResponse[]>>() {
            @Override
            public void onChanged(@Nullable Resource<AlbumResponse[]> resource) {
                processResponse(resource);
            }
        });

        init();
    }

    private void init(){
        adapter = new RvAlbumAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.rvAlbum.setLayoutManager(layoutManager);
        binding.rvAlbum.setAdapter(adapter);

        mGlobals = new Globals();
    }

    private void processResponse(Resource resource){
        switch (resource.getStatus()){
            case LOADING:
                binding.setShowProgress(true);
                break;
            case SUCCESS: {
                binding.setShowProgress(false);
                AlbumResponse[] resourceData = (AlbumResponse[]) resource.getData();
                adapter.update(getAlbumList(resourceData));
                break;
            }
            case ERROR:
                binding.setShowProgress(false);
                AlbumResponse[] resourceData = (AlbumResponse[]) resource.getData();
                if(resourceData!=null){
                    mGlobals.showAlertDialog(Integer.toString(resourceData.length),this);
                }else{mGlobals.showAlertDialog("Request Failed Try again....",this);
                }
                break;
        }

    }

    private List<AlbumResponse> getAlbumList(AlbumResponse[] resourceData){
        List<AlbumResponse> albumResponseList = new ArrayList<>();
        for(AlbumResponse albumResponse:resourceData){
            albumResponseList.add(albumResponse);
        }
        return albumResponseList;
    }

    @Override
    public void gotoAlbumPhoto(Integer albumId) {
        Intent intent = new Intent(this,PhotosActivity.class);
        intent.putExtra("albumId",albumId);
        startActivity(intent);
    }
}
