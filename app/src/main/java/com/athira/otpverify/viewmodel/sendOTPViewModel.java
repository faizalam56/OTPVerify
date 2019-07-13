package com.athira.otpverify.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.athira.otpverify.dto.Resource;
import com.athira.otpverify.model.SendOTPResponse;
import com.athira.otpverify.repository.SendOTPRepository;

import java.util.Map;

public class sendOTPViewModel extends ViewModel {
    private MutableLiveData<Request> loginRequest = new MutableLiveData<>();
    private LiveData<Resource<SendOTPResponse>> loginResult = Transformations.switchMap(loginRequest, new Function<Request, LiveData<Resource<SendOTPResponse>>>() {
        @Override
        public LiveData<Resource<SendOTPResponse>> apply(Request input) {
            LiveData<Resource<SendOTPResponse>> resourceLiveData = new SendOTPRepository().callSendOTPApi(input.option);
            return resourceLiveData;
        }
    });
    public LiveData<Resource<SendOTPResponse>> getOTP(){

        return this.loginResult;
    }

    public static class Request {
        final private Map<String,String> option;
        public Request(Map<String,String> option) {
            this.option = option;

        }
    }

    public void callSendOTPApi(Map<String,String> option){
        loginRequest.setValue(new Request(option));
    }
}
