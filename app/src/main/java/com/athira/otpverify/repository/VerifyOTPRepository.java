package com.athira.otpverify.repository;

import android.arch.lifecycle.MutableLiveData;

import com.athira.otpverify.dto.Resource;
import com.athira.otpverify.exception.AppException;
import com.athira.otpverify.model.SendOTPResponse;
import com.athira.otpverify.model.VerifyOTPResponse;
import com.athira.otpverify.network.APIClient;
import com.athira.otpverify.network.APIInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOTPRepository {

    private APIInterface apiInterface;
    public VerifyOTPRepository(){
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    public MutableLiveData<Resource<VerifyOTPResponse>> callVerifyOTPApi(Map<String,String> option){
        final MutableLiveData<Resource<VerifyOTPResponse>> verifyOTPLiveData = new MutableLiveData<>();

        verifyOTPLiveData.setValue(Resource.<VerifyOTPResponse>loading());

        Call<JsonObject> call = apiInterface.verifyOTP(option);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Gson gson = new Gson();
                if(response.body()!=null){
                    VerifyOTPResponse otpResponse = gson.fromJson(response.body(),VerifyOTPResponse.class);
                    //JsonObject resource = response.body();
                    verifyOTPLiveData.setValue(Resource.<VerifyOTPResponse>success(otpResponse));

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                call.cancel();
                verifyOTPLiveData.setValue(Resource.<VerifyOTPResponse>error(new AppException(t),null));
            }
        });
        return verifyOTPLiveData;
    }
}
