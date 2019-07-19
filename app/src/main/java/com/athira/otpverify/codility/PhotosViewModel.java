package com.athira.otpverify.codility;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.athira.otpverify.dto.Resource;

import java.util.Map;

public class PhotosViewModel extends ViewModel {
    private MutableLiveData<Request> photosRequest = new MutableLiveData<>();
    private LiveData<Resource<PhotosResponse[]>> photosResult = Transformations.switchMap(photosRequest, new Function<Request, LiveData<Resource<PhotosResponse[]>>>() {
        @Override
        public LiveData<Resource<PhotosResponse[]>> apply(Request input) {
            LiveData<Resource<PhotosResponse[]>> resourceLiveData = new PhotosRepository().callPhotosApi(input.option);
            return resourceLiveData;
        }
    });
    public LiveData<Resource<PhotosResponse[]>> getPhotos(){

        return this.photosResult;
    }

    public static class Request {
        Map<String,String> option;
        public Request(Map<String,String> option) {
            this.option = option;
        }
    }

    public void callPhotosApi(Map<String,String> option){
        photosRequest.setValue(new Request(option));
    }
}
