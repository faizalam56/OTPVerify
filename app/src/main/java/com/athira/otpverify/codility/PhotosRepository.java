package com.athira.otpverify.codility;

import android.arch.lifecycle.MutableLiveData;

import com.athira.otpverify.dto.Resource;
import com.athira.otpverify.exception.AppException;
import com.athira.otpverify.network.APIClient;
import com.athira.otpverify.network.APIInterface;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotosRepository {

    private APIInterface apiInterface;
    public PhotosRepository(){
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    public MutableLiveData<Resource<PhotosResponse[]>> callPhotosApi(Map<String,String> option){
        final MutableLiveData<Resource<PhotosResponse[]>> photosLiveData = new MutableLiveData<>();
        photosLiveData.setValue(Resource.<PhotosResponse[]>loading());
        Call<JsonArray> call = apiInterface.getPhotos(option);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                Gson gson = new Gson();
                if(response.body()!=null){
                    PhotosResponse[] photosResponses = gson.fromJson(response.body(),PhotosResponse[].class);
                    photosLiveData.setValue(Resource.success(photosResponses));

                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                call.cancel();
                photosLiveData.setValue(Resource.<PhotosResponse[]>error(new AppException(t),null));
            }
        });
        return photosLiveData;
    }
}
