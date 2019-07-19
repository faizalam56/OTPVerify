package com.athira.otpverify.codility;

import android.arch.lifecycle.MutableLiveData;

import com.athira.otpverify.dto.Resource;
import com.athira.otpverify.exception.AppException;
import com.athira.otpverify.network.APIClient;
import com.athira.otpverify.network.APIInterface;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumRepository {
    private APIInterface apiInterface;
    public AlbumRepository(){
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    public MutableLiveData<Resource<AlbumResponse[]>> callAlbumApi(){
        final MutableLiveData<Resource<AlbumResponse[]>> albumLiveData = new MutableLiveData<>();
        albumLiveData.setValue(Resource.<AlbumResponse[]>loading());
        Call<JsonArray> call = apiInterface.getAlbum();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                Gson gson = new Gson();
                if(response.body()!=null){
                    AlbumResponse[] albumResponses = gson.fromJson(response.body(),AlbumResponse[].class);
                    albumLiveData.setValue(Resource.success(albumResponses));
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                call.cancel();
                albumLiveData.setValue(Resource.<AlbumResponse[]>error(new AppException(t),null));
            }
        });
        return albumLiveData;
    }
}
