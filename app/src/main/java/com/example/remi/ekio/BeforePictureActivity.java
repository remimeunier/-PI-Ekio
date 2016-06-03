package com.example.remi.ekio;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.Date;

public class BeforePictureActivity extends AppCompatActivity {
    public final static String MESSAGE_KEY = "com.example.remi.ekio.messagekey";
    long time = System.currentTimeMillis();
    String name = String.valueOf(time)+".jpg";

    // gestion du menu (voir main activity for details)
    private String[] mMenuItem;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private LinearLayout point;


    // camera
    ImageView findingLoupe;
    ImageView preview;
    ImageButton chooseToFind, chooseToSave;
    TextView appVersion;
    static final int CAM_REQUEST =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // gestion du menu (voir main activity for details)
        mMenuItem = getResources().getStringArray(R.array.menu_item);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        CustomListAdapter adapter=new CustomListAdapter(this, mMenuItem);
        appVersion = (TextView) findViewById(R.id.app_version);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // gestion du main content
        LayoutInflater factory = LayoutInflater.from(this);
        View myView = factory.inflate(R.layout.loupe, null);
        point = (LinearLayout) findViewById(R.id.point);
        point.addView(myView);

        //taking the picture
        findingLoupe = (ImageView) findViewById(R.id.findingLoupe);
        preview = (ImageView) findViewById(R.id.preview_display);
        chooseToSave = (ImageButton) findViewById(R.id.choosToSave);
        chooseToFind = (ImageButton) findViewById(R.id.chooseToFind);


        findingLoupe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                File file = getFile();
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

                startActivityForResult(camera_intent,CAM_REQUEST);

            }
        });
    }

    public void userChoice(View view){
        int button_id;
        button_id = ((ImageButton) view).getId();

        if(button_id == (R.id.choosToSave)){
            Intent save = new Intent(this, SaveInfoActivity.class);
            save.putExtra(MESSAGE_KEY, name);
            startActivity(save);
        }
        else if(button_id== (R.id.chooseToFind)){
            //TODO
        }
    }


    private File getFile(){
        File ekioFolder = new File("sdcard/EkioPhotos");
        if(!ekioFolder.exists()){
            ekioFolder.mkdir();
        }


        File image_file = new File(ekioFolder, name);
        return image_file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAM_REQUEST){
            if(resultCode == RESULT_OK){

                String path = "sdcard/EkioPhotos/"+name;
                findingLoupe.setOnClickListener(null);
                findingLoupe.setImageBitmap(null);
                preview.setImageDrawable(Drawable.createFromPath(path));

                chooseToSave.setClickable(true);
                chooseToFind.setClickable(true);
                chooseToSave.setImageResource(R.drawable.save);
                chooseToFind.setImageResource(R.drawable.find);


            }
            else if(resultCode == RESULT_CANCELED){

                //capture canceled
            }
            else{
                //capture fail
            }
        }
    }
}
