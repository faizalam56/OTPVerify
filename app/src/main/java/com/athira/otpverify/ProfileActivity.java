package com.athira.otpverify;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.athira.otpverify.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private ActivityProfileBinding binding;
    SharedAppPreference sharedAppPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.activity_profile,null,false);
        setContentView(binding.getRoot());

        //String phone=getIntent().getStringExtra("phone");
        sharedAppPreference = new SharedAppPreference(this);
        if(sharedAppPreference.getContains("LoginUser")) {
            binding.setPhone(sharedAppPreference.getString("LoginUser"));
        }
        binding.setProfileActivity(this);
    }

    PopupMenu popupmenu;
    public void showSettingMenu(){
        popupmenu = new PopupMenu(this,binding.ivSettings);
        popupmenu.inflate(R.menu.options_menu);
        popupmenu.setOnMenuItemClickListener(this);
        popupmenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.option_logout:
                popupmenu.dismiss();
                showDialog("are you sure want to logout?");
                return true;
        }
        return false;
    }

    private void showDialog(String message){
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        final TextView title = new TextView(this);
        title.setText("Alert");
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.parseColor("#4182ac"));
        title.setTextSize(25);
        myDialog.setCustomTitle(title);
        myDialog.setMessage(message);
        myDialog.setCancelable(false);

        myDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                {
                    logout();
                }
            }
        });
        myDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        myDialog.show();
    }

    private void logout(){
        SharedAppPreference sharedAppPreference = new SharedAppPreference(this);
        sharedAppPreference.logout();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
