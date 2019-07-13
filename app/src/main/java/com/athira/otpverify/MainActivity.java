package com.athira.otpverify;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.athira.otpverify.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SharedAppPreference sharedAppPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.activity_main,null,false);
        setContentView(binding.getRoot());
        binding.setMainActivity(this);
        sharedAppPreference = new SharedAppPreference(this);
        if(sharedAppPreference.getContains("LoginUser")){
            startActivity(new Intent(this,ProfileActivity.class));
            this.finish();
        }
    }

    public void signUp(){
        startActivity(new Intent(this,SignupActivity.class));
    }
}
