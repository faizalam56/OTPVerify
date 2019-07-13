package com.athira.otpverify.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

public class Global {
    AlertDialog alertDialog;
    public void showAlertDialog(String message, Context ActivityContext) {
        final AlertDialog.Builder myDialog = new AlertDialog.Builder(
                ActivityContext);
        final TextView title = new TextView(ActivityContext);
        title.setText("Alert");
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.parseColor("#4182ac"));
        title.setTextSize(25);
        myDialog.setCustomTitle(title);
        myDialog.setMessage(message);
        myDialog.setCancelable(false);

        myDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int arg1) {
                dialogInterface.cancel();
            }
        });

        myDialog.show();

    }
}
