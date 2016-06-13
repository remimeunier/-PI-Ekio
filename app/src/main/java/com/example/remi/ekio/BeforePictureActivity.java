package com.example.remi.ekio;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.system.ErrnoException;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class BeforePictureActivity extends AppCompatActivity {
    public final static String MESSAGE_KEY = "com.example.remi.ekio.messagekey";
    public final static String MESSAGE_RES = "com.example.remi.ekio.messageres";
    public final static String temp = "sdcard/EkioPhotos/tmp/tempfile.jpg";
    long time = System.currentTimeMillis();
    String name = String.valueOf(time)+".jpg";
    private final Rect Square = new Rect(300,300,300,300);
    ArrayList<Integer> result;

    // gestion du menu (voir main activity for details)
    private String[] mMenuItem;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private LinearLayout point;

    static {
        // If you use opencv 2.4, System.loadLibrary("opencv_java")
        System.loadLibrary("opencv_java3");
    }


    // camera
    ImageView findingLoupe;
    CropImageView mCropImageView;
    ImageButton chooseToFind, chooseToSave, choosetoCrop;
    Uri mCropImageUri;
    ImageView C1, C2;
    static final int CAM_REQUEST =1;
    Bitmap savedPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File ekioFolder = new File("sdcard/EkioPhotos");
        if(!ekioFolder.exists()){
            ekioFolder.mkdir();
        }

        File tempFolder = new File("sdcard/EkioPhotos/tmp");
        if(!ekioFolder.exists()){
            ekioFolder.mkdir();
        }


        // gestion du menu (voir main activity for details)
        mMenuItem = getResources().getStringArray(R.array.menu_item);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        CustomListAdapter adapter=new CustomListAdapter(this, mMenuItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // gestion du main content
        LayoutInflater factory = LayoutInflater.from(this);
        View myView = factory.inflate(R.layout.loupe, null);
        point = (LinearLayout) findViewById(R.id.point);
        point.addView(myView);

        //taking the picture
        findingLoupe = (ImageView) findViewById(R.id.findingLoupe);
        mCropImageView = (CropImageView) findViewById(R.id.CropImageView);
        //mCropImageView.setCropRect(Square);
       // mCropImageView.setAspectRatio(1,1);
       // mCropImageView.setFixedAspectRatio(true);
        mCropImageView.setScaleType(CropImageView.ScaleType.FIT_CENTER);
        chooseToSave = (ImageButton) findViewById(R.id.choosToSave);
        chooseToFind = (ImageButton) findViewById(R.id.chooseToFind);
        choosetoCrop = (ImageButton) findViewById(R.id.chooseTocrop);
        C1 = (ImageView) findViewById(R.id.circle1);
        C2 = (ImageView) findViewById(R.id.circle2);


        result = new ArrayList();
    }







    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == Activity.RESULT_OK) {
            Uri imageUri = getPickImageResultUri(data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage,
            // but we don't know if we need to for the URI so the simplest is to try open the stream and see if we get error.
            boolean requirePermissions = false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    isUriRequiresPermissions(imageUri)) {

                // request permissions and handle the result in onRequestPermissionsResult()
                requirePermissions = true;
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }

            if (!requirePermissions) {
                String path = "sdcard/EkioPhotos/"+name;
                findingLoupe.setOnClickListener(null);
                findingLoupe.setImageBitmap(null);

                mCropImageView.setImageUriAsync(imageUri);
                C1.setImageBitmap(null);
                C2.setImageBitmap(null);

                choosetoCrop.setClickable(true);
                choosetoCrop.setImageResource(R.drawable.crop);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            mCropImageView.setImageUriAsync(mCropImageUri);

        } else {
            Toast.makeText(this, "Required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }


    private void saveFile(Bitmap bmp, String filename){
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filename);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * On load image button click, start pick image chooser activity.
     */
    public void onLoadImageClick(View view) {
        startActivityForResult(getPickImageChooserIntent(), 200);

    }


    /**
     * Crop the image and set it back to the cropping view.
     */
    public void onCropImageClick(View view) {
        Bitmap cropped = mCropImageView.getCroppedImage(500, 500);
        if (cropped != null)
            mCropImageView.setImageBitmap(cropped);
            mCropImageView.setShowCropOverlay(false);
            mCropImageView.setCropRect(null);
            savedPhoto = cropped;

            saveFile(cropped,temp );
            choosetoCrop.setClickable(false);
            choosetoCrop.setImageBitmap(null);
            chooseToSave.setClickable(true);
            chooseToFind.setClickable(true);
            chooseToSave.setImageResource(R.drawable.save);
            chooseToFind.setImageResource(R.drawable.find);
            mCropImageView.setBackgroundColor(Color.WHITE);
    }

    public void userChoice(View view){
        int button_id;
        button_id = ((ImageButton) view).getId();

        if(button_id == (R.id.choosToSave)){
            String path = "sdcard/EkioPhotos/"+name;
            saveFile(savedPhoto, path);
            Intent save = new Intent(this, SaveInfoActivity.class);
            save.putExtra(MESSAGE_KEY, name);
            startActivity(save);
        }
        else if(button_id== (R.id.chooseToFind)){
            // TODO: 10/06/16

             String path = "sdcard/EkioPhotos/tmp/"+name;
            saveFile(savedPhoto, path);
//
            ObjectDetection test2 = new ObjectDetection(path, this);
            test2.match2();


            //CollectionableDAO objectDao = new CollectionableDAO(this);
//            objectDao.open();
//            ArrayList<String> list = objectDao.allPath();
//            objectDao.close();

//            ObjectDetection test = new ObjectDetection();
//            int i = 1;
//            int good, better, best;
//            good = better = best = 0;
//            for (String path : list) {
//
//                int x = test.match(temp, path);
//                //Bitmap myBitmap = BitmapFactory.decodeFile(objectDao.select(5).getPhotoPath());
//                //mCropImageView.setImageBitmap(myBitmap);
//
//                if (x > best){
//                    good = better;
//                    better = best;
//                    best = x;
//                } else if (x >better){
//                    good = better;
//                    better = x;
//                } else good = x;
//
//                //Toast.makeText(getApplicationContext(), String.valueOf(x) + " with " + String.valueOf(i), Toast.LENGTH_SHORT).show();
//
//                result.add(x);
//                i++;
//            }

//            String res = String.valueOf(good) +"," + String.valueOf(better) +"," + String.valueOf(best);
//            Intent showRes = new Intent(this, GoodMatchActivity.class);
//            showRes.putExtra(MESSAGE_RES, res);
//            startActivity(showRes);

        }
    }

    /**
     * Create a chooser intent to select the source to get image from.<br/>
     * The source can be camera's (ACTION_IMAGE_CAPTURE) or gallery's (ACTION_GET_CONTENT).<br/>
     * All possible sources are added to the intent chooser.
     */
    public Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList();
        PackageManager packageManager = getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    /**
     * Get URI to image received from capture by camera.
     */
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "pickImageResult.jpeg"));
        }
        return outputFileUri;
    }

    /**
     * Get the URI of the selected image from {@link #getPickImageChooserIntent()}.<br/>
     * Will return the correct URI for camera and gallery image.
     *
     * @param data the returned data of the activity result
     */
    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null && data.getData() != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    /**
     * Test if we can open the given Android URI to test if permission required error is thrown.<br>
     */
    public boolean isUriRequiresPermissions(Uri uri) {
        try {
            ContentResolver resolver = getContentResolver();
            InputStream stream = resolver.openInputStream(uri);
            stream.close();
            return false;
        } catch (FileNotFoundException e) {
            if (e.getCause() instanceof ErrnoException) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

}
