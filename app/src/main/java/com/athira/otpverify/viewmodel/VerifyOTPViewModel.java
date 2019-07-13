package com.athira.otpverify.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.athira.otpverify.dto.Resource;
import com.athira.otpverify.model.VerifyOTPResponse;
import com.athira.otpverify.repository.VerifyOTPRepository;

import java.util.Map;

public class VerifyOTPViewModel extends ViewModel {
    private MutableLiveData<Request> loginRequest = new MutableLiveData<>();
    private LiveData<Resource<VerifyOTPResponse>> loginResult = Transformations.switchMap(loginRequest, new Function<Request, LiveData<Resource<VerifyOTPResponse>>>() {
        @Override
        public LiveData<Resource<VerifyOTPResponse>> apply(Request input) {
            LiveData<Resource<VerifyOTPResponse>> resourceLiveData = new VerifyOTPRepository().callVerifyOTPApi(input.option);
            return resourceLiveData;
        }
    });
    public LiveData<Resource<VerifyOTPResponse>> verifyOTP(){

        return this.loginResult;
    }

    public static class Request {
        final private Map<String,String> option;
        public Request(Map<String,String> option) {
            this.option = option;

        }
    }

    public void callVerifyOTPApi(Map<String,String> option){
        loginRequest.setValue(new Request(option));
    }
}
