package com.athira.otpverify.codility;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.athira.otpverify.dto.Resource;

public class AlbumViewModel extends ViewModel {

    private MutableLiveData<Request> albumRequest = new MutableLiveData<>();
    private LiveData<Resource<AlbumResponse[]>> albumResult = Transformations.switchMap(albumRequest, new Function<Request, LiveData<Resource<AlbumResponse[]>>>() {
        @Override
        public LiveData<Resource<AlbumResponse[]>> apply(Request input) {
            LiveData<Resource<AlbumResponse[]>> resourceLiveData = new AlbumRepository().callAlbumApi();
            return resourceLiveData;
        }
    });
    public LiveData<Resource<AlbumResponse[]>> getAlbum(){

        return this.albumResult;
    }

    public static class Request {

        public Request() {

        }
    }

    public void callAlbumApi(){
        albumRequest.setValue(new Request());
    }
}
