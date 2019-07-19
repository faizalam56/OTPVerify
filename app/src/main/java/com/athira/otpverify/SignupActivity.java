package com.athira.otpverify;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.athira.otpverify.databinding.ActivitySignupBinding;
import com.athira.otpverify.dto.Resource;
import com.athira.otpverify.model.SendOTPResponse;
import com.athira.otpverify.model.VerifyOTPResponse;
import com.athira.otpverify.utils.Globals;
import com.athira.otpverify.viewmodel.VerifyOTPViewModel;
import com.athira.otpverify.viewmodel.sendOTPViewModel;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;
    private boolean mOtpSent = false;
    private sendOTPViewModel mSendViewModel;
    private VerifyOTPViewModel mVerifyViewModel;
    private Globals mGlobals;
    private String appOTP;
    private String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.activity_signup,null,false);
        setContentView(binding.getRoot());
        binding.setSignupActivity(this);
        mGlobals = new Globals();
        sendOtpProvider();
        verifyOtpProvider();
    }

    private void sendOtpProvider(){
        mSendViewModel = ViewModelProviders.of(this).get(sendOTPViewModel.class);
        mSendViewModel.getOTP().observe(this, new Observer<Resource<SendOTPResponse>>() {
            @Override
            public void onChanged(@Nullable Resource<SendOTPResponse> sendOTPResponseResource) {
                sendProcessResponse(sendOTPResponseResource);
            }
        });
    }

    private void verifyOtpProvider(){
        mVerifyViewModel = ViewModelProviders.of(this).get(VerifyOTPViewModel.class);
        mVerifyViewModel.verifyOTP().observe(this, new Observer<Resource<VerifyOTPResponse>>() {
            @Override
            public void onChanged(@Nullable Resource<VerifyOTPResponse> verifyOTPResponseResource) {
                verifyProcessResponse(verifyOTPResponseResource);
            }
        });
    }

    public void sendOTP(String mobile,String otp){
        if(mOtpSent){
            this.phone = mobile;
            if(otp.equals(appOTP)) {
                mVerifyViewModel.callVerifyOTPApi(getQueryMap(mobile, otp));
            }else{
                binding.etOtp.setText("");
                Toast.makeText(this, "Please enter a correct OTP", Toast.LENGTH_SHORT).show();
            }
        }else if(mobile==null||mobile.length()<10){
            Toast.makeText(this, "Please enter a correct phone no.", Toast.LENGTH_SHORT).show();
        }else{
            mSendViewModel.callSendOTPApi(getSendOtpQueryMap(mobile));

            binding.setLoading(true);
            mOtpSent = true;
            binding.setLine(true);
            binding.setVerifyCode(true);
            binding.setResend(true);
            binding.setChangePhone(true);
            binding.etPhone.setFocusable(false);
            binding.tvSend.setText("Next");
            binding.etOtp.setText("");
        }
    }
    public void verifyOTP(){

    }
    public void changePhone(){
        binding.etPhone.setText("");
        Toast.makeText(this, "Please enter your new no.", Toast.LENGTH_SHORT).show();
        binding.etPhone.setFocusableInTouchMode(true);
        binding.setLine(true);
        binding.setVerifyCode(false);
        binding.setResend(false);
        binding.setChangePhone(false);
        mOtpSent = false;
        binding.tvSend.setText("Send OTP");
    }

    public void resendOTP(String phone){
        mSendViewModel.callSendOTPApi(getSendOtpQueryMap(phone));
        binding.etOtp.setText("");
        Toast.makeText(this, "New OTP sent.", Toast.LENGTH_SHORT).show();
    }

    private void sendProcessResponse(Resource resource){
        switch (resource.getStatus()){
            case LOADING:
                binding.setLoading(true);
                break;
            case SUCCESS: {
                binding.setLoading(false);
                SendOTPResponse resourceData = (SendOTPResponse) resource.getData();
                appOTP = resourceData.getOtp();
                Toast.makeText(this, resourceData.getStatus()+" :"+resourceData.getOtp(), Toast.LENGTH_SHORT).show();
                break;
            }
            case ERROR:
                binding.setLoading(false);
                SendOTPResponse resourceData= (SendOTPResponse) resource.getData();
                if(resourceData!=null){
                    mGlobals.showAlertDialog(resourceData.getStatus(),this);
                }else{mGlobals.showAlertDialog("Request Failed Try again....",this);
                }
                break;
        }

    }

    private void verifyProcessResponse(Resource resource){
        switch (resource.getStatus()){
            case LOADING:
                binding.setLoading(true);
                break;
            case SUCCESS: {
                binding.setLoading(false);
                VerifyOTPResponse resourceData = (VerifyOTPResponse) resource.getData();
                Toast.makeText(this, resourceData.getStatus(), Toast.LENGTH_SHORT).show();
                profileActivity();
                break;
            }
            case ERROR:
                binding.setLoading(false);
                VerifyOTPResponse resourceData= (VerifyOTPResponse) resource.getData();
                if(resourceData!=null){
                    mGlobals.showAlertDialog(resourceData.getStatus(),this);
                }else{mGlobals.showAlertDialog("Request Failed Try again....",this);
                }
                break;
        }

    }

    private void profileActivity(){
        SharedAppPreference sharedAppPreference = new SharedAppPreference(this);
        sharedAppPreference.putString("LoginUser",phone);
        Intent intent = new Intent(this,ProfileActivity.class);
        intent.putExtra("phone",phone);
        startActivity(intent);
        this.finish();
    }

    private Map<String,String> getSendOtpQueryMap(String phone){
            Map<String,String> data = new HashMap<>();
        data.put("phone",phone);
        data.put("type","set_otp");

        return data;
    }
    private Map<String,String> getQueryMap(String phone,String otp){
        Map<String,String> data = new HashMap<>();
        data.put("phone",phone);
        data.put("type","verify_otp");
        data.put("otp",otp);

        return data;
    }
}
