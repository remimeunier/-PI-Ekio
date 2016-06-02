package com.example.remi.ekio;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;


import java.io.FileNotFoundException;

/**
 * Created by Hoang Nam on 31/05/2016.
 */
public class PhotoGrandEcranACtivity extends Activity {

    public final static String MESSAGE_KEY = "com.example.remi.ekio.messagekey";
    ImageView iv;
    ImageButton fav;
    TextView object_name, object_date, object_key_words, object_comment, object_location;

    // gestion du menu (voir main activity for details)
    private String[] mMenuItem;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private LinearLayout point;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // gestion du menu (voir main activity for details)
        mMenuItem = getResources().getStringArray(R.array.menu_item);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        CustomListAdapter adapter=new CustomListAdapter(this, mMenuItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // gestion du main content
        LayoutInflater factory = LayoutInflater.from(this);
        View myView = factory.inflate(R.layout.resultat_layout, null);
        point = (LinearLayout) findViewById(R.id.point);
        point.addView(myView);


        Intent intent = getIntent();
        //File file = intent.getExtras().getParcelable(("img"));
        //String file = intent.getStringExtra("img");

        //get the ID send throught intent
        int id = intent.getIntExtra(MESSAGE_KEY,1);

        //get the object matching the id
        CollectionableDAO objectDao = new CollectionableDAO(this);
        objectDao.open();
        Collectionable object = objectDao.select(id);
        objectDao.close();

        //show the image with size downgrading
        iv = (ImageView) findViewById(R.id.result_photo);

        Uri selectedImage = Uri.parse("file:///"+object.getPhotoPath());
        try {
            iv.setImageBitmap(decodeUri(selectedImage));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

       // iv.setImageURI(Uri.parse(object.getPhotoPath()));
       // Uri image = Uri.parse(object.getPhotoPath());

        //methode x
  //      File imgFile = new  File(object.getPhotoPath());
  //      if(imgFile.exists()){
  //          Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
  //          int newVal = (350 * myBitmap.getWidth()) / myBitmap.getHeight();
  //          myBitmap.getWidth();
  //          getResizedBitmap(myBitmap, 350, newVal);
  //          iv.setImageBitmap(myBitmap);

   //     }

        //3
  //      iv.setImageDrawable(Drawable.createFromPath(object.getPhotoPath()));

        //1
//        Matrix matrix = new Matrix();
  //      iv.setScaleType(ImageView.ScaleType.MATRIX);   //required
        //matrix.postRotate((float) 90, 0, 0);
   //     iv.setImageMatrix(matrix);
        //3
        //iv.setImageBitmap(decodeUri(selectedImage));
        //imgbt.setImageUri(Uri.fromFile(new File("/data/data/....")));

        // ???

  //      fav = (ImageButton) findViewById(R.id.fav);
  //      fav.setTag(R.drawable.fav_off);
        // show title, date, comment (to do)
        object_name = (TextView) findViewById(R.id.result_photo_title);
        object_name.setText(object.getTitle());
        object_date = (TextView) findViewById(R.id.result_photo_date);
        object_date.setText(object.getDate());
        object_location = (TextView) findViewById(R.id.result_photo_location);
        object_location.setText(object.getLocation());
        object_comment = (TextView) findViewById(R.id.result_photo_comment);
        object_comment.setText(object.getcomment());
        object_key_words = (TextView) findViewById(R.id.result_keyword);
        object_key_words.setText(object.getKeyWords());

    }

    public void showDetails(View view) {

    }


    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                getContentResolver().openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 100;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(
                getContentResolver().openInputStream(selectedImage), null, o2);
    }

/*    public static Bitmap getResizedBitmap(Bitmap image, int newHeight, int newWidth) {
        int width = image.getWidth();
        int height = image.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(image, 0, 0, width, height,
                matrix, false);
        return resizedBitmap;
    }*/

    /*    public void favorise(View view) {
        if (fav.getTag().equals(R.drawable.fav_off)) {
            fav.setImageResource(R.drawable.fav_on);
            fav.setTag(R.drawable.fav_on);
            Toast.makeText(getApplicationContext(),
                    object_name.getText().toString() + "has been saved to Favorites", Toast.LENGTH_SHORT).show();

        } else {
            fav.setImageResource(R.drawable.fav_off);
            fav.setTag(R.drawable.fav_off);
            Toast.makeText(getApplicationContext(),
                    object_name.getText().toString() + "has been removed from Favorites", Toast.LENGTH_SHORT).show();

        }
    }*/

    public void goCollectionShowcase(View view){

        Intent intent = new Intent(this, CollectionShowcaseActivity.class);
        startActivity(intent);
    }

}
