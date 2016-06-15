package com.example.remi.ekio;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Hoang Nam on 15/06/2016.
 */
public class SettingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

    }
    public void popUpChangePass(View view){
        AlertDialog pass = changePassPopUp();
        pass.show();
    }
    public void popUpLogOut(View view){
        AlertDialog logout = logOutPopUp();
        logout.show();
    }
    public void popUpInfo(View view){
        AlertDialog info = appInfoPopUp();
        info.show();
    }
    public void popUpRateUs(View view){
        AlertDialog rate = rateUsPopUp();
        rate.show();
    }

    private AlertDialog changePassPopUp(){
        LayoutInflater inflater = this.getLayoutInflater();

        AlertDialog temp =  new AlertDialog.Builder(this)
                        .setTitle("Change your password")
                        .setIcon(R.drawable.edit_icon)
                        .setView(inflater.inflate(R.layout.change_pass, null))
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // check pass, change pass
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                           @Override
                                public void onClick(DialogInterface dialog, int id) {
                               dialog.dismiss();
                           }
                        })
                        .create();
        return temp;
    }

    private AlertDialog logOutPopUp(){
        LayoutInflater inflater = this.getLayoutInflater();

        AlertDialog temp =  new AlertDialog.Builder(this)
                .setTitle("Log out ?")
                .setIcon(R.drawable.info_icon)
                .setMessage("Do you really want to log out ?")
                .setPositiveButton("Get me the F*** out, please ?", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //log out
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Later", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .create();
        return temp;
    }

    private AlertDialog rateUsPopUp(){
        LayoutInflater inflater = this.getLayoutInflater();

        AlertDialog temp =  new AlertDialog.Builder(this)
                .setTitle("Rate us!")
                .setIcon(R.drawable.rate_icon)
                .setMessage("If you enjoy our application, please give us 5 stars.")
                .setPositiveButton("Rate now !", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), "Please find us on Google Play, thank you.", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                })
                .setNeutralButton("No, thanks", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Later", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .create();
        return temp;
    }

    private AlertDialog appInfoPopUp(){
        LayoutInflater inflater = this.getLayoutInflater();

        AlertDialog temp =  new AlertDialog.Builder(this)
                .setTitle("Application Information")
                .setIcon(R.drawable.info_icon)
                .setView(inflater.inflate(R.layout.app_info, null))
                .setNegativeButton("Close", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .create();
        return temp;
    }

}
