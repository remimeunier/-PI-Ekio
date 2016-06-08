package com.example.remi.ekio;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    private String[] mMenuItem;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private LinearLayout point;

    static {
        // If you use opencv 2.4, System.loadLibrary("opencv_java")
        System.loadLibrary("opencv_java3");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);

        CollectionableDAO objectDao = new CollectionableDAO(this);
        objectDao.open();
        Collectionable object = objectDao.select(1);
        objectDao.close();

        String object_filename = object.getPhotoPath();
        String scene_filename = object.getPhotoPath();

        AkazeImageFinder finder = new AkazeImageFinder();
        finder.rotation = "0 degrees";
        finder.findImage(object_filename, scene_filename);
        


//
//        //sign In (if user created and non auto conexion) or Register (no account) or direct connect (auto connexion)
//        UserDAO userDao = new UserDAO(this);
//        userDao.open();
//        User user = userDao.selectionner(1);
//        userDao.close();
//        if (user == null) {
//            final ImageView imSignIn = (ImageView) findViewById(R.id.login);
//            imSignIn.setVisibility(View.GONE);
//        } else {
//            if (user.getAuto() == true) {
//                Intent intent = new Intent(this, BeforePictureActivity.class);
//                startActivity(intent);
//            } else {
//                final ImageView imRegister = (ImageView) findViewById(R.id.register);
//                imRegister.setVisibility(View.GONE);
//            }
//        }
    }

    public void goSignIn(View view) {
        Intent goSignIn = new Intent(this, SignInActivity.class);
        startActivity(goSignIn);
    }
    public void goRegister(View view) {
        Intent goToRegister = new Intent(this, RegisterActivity.class);
        startActivity(goToRegister);
    }

}
