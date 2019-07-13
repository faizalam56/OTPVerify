package com.athira.otpverify.repository;

import android.arch.lifecycle.MutableLiveData;

import com.athira.otpverify.dto.Resource;
import com.athira.otpverify.exception.AppException;
import com.athira.otpverify.model.SendOTPResponse;
import com.athira.otpverify.network.APIClient;
import com.athira.otpverify.network.APIInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendOTPRepository {

    private APIInterface apiInterface;

    public SendOTPRepository(){
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    public MutableLiveData<Resource<SendOTPResponse>> callSendOTPApi(Map<String,String> option){
        final MutableLiveData<Resource<SendOTPResponse>> sendOTPLiveData = new MutableLiveData<>();

        sendOTPLiveData.setValue(Resource.<SendOTPResponse>loading());

        Call<JsonObject> call = apiInterface.getOTP(option);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Gson gson = new Gson();
                if(response.body()!=null){
                    SendOTPResponse otpResponse = gson.fromJson(response.body(),SendOTPResponse.class);
                    //JsonObject resource = response.body();

                        if(otpResponse.getStatus().equals("otp_set_success")) {
                            sendOTPLiveData.setValue(Resource.<SendOTPResponse>success(otpResponse));
                        }else {
                            sendOTPLiveData.setValue(Resource.<SendOTPResponse>error(null, otpResponse));
                        }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                call.cancel();
                sendOTPLiveData.setValue(Resource.<SendOTPResponse>error(new AppException(t),null));
            }
        });
        return sendOTPLiveData;
    }
}
