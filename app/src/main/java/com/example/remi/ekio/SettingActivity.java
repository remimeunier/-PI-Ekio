package com.example.remi.ekio;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

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
                .setTitle("Log Out")
                .setIcon(R.drawable.info_icon)
                .setMessage("Do you really want to log out ?")
                .setPositiveButton("Log the F*** out, please", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Log the fuck out
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .create();
        return temp;
    }


}
