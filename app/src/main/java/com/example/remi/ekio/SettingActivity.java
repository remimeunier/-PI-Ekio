package com.example.remi.ekio;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
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

    private AlertDialog changePassPopUp(){
        LayoutInflater inflater = this.getLayoutInflater();
        final Context context = this;

        AlertDialog temp =  new AlertDialog.Builder(this)
                        .setTitle("Change your password")
                        .setIcon(R.drawable.edit_icon)
                        .setView(inflater.inflate(R.layout.change_pass, null))
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener(){
                            final EditText etOld = (EditText) findViewById(R.id.oldPassword);
                            final EditText etNew1 = (EditText) findViewById(R.id.newPassword);
                            final EditText etNew2 = (EditText) findViewById(R.id.confirmNewPassword);

                            final String old = etOld.getText().toString();
                            final String newP = etNew1.getText().toString();
                            final String newP2 = etNew2.getText().toString();

                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                UserDAO userDao = new UserDAO(context);
                                userDao.open();
                                Boolean valid = userDao.authentificate(old);
                                userDao.close();

                                if (valid) {
                                    if (!newP.isEmpty() && newP.equals(newP2)) {
                                        UserDAO userDao2 = new UserDAO(context);
                                        userDao2.open();
                                        userDao.modifierPassword(newP);
                                        userDao2.close();
                                    }

                                } else {
                                    etOld.getText().clear();
                                    Toast.makeText(SettingActivity.this, "Password not valid, please try again.",
                                            Toast.LENGTH_LONG).show();
                                }

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
                        int pid = android.os.Process.myPid();
                        android.os.Process.killProcess(pid);
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(intent);
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
