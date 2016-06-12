package com.example.remi.ekio;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.content.Context;

import android.widget.AdapterView.OnItemClickListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class CollectionShowcaseActivity extends Activity {

    public final static String MESSAGE_KEY = "com.example.remi.ekio.messagekey";
    private GridView gv;
    //ArrayList<File> list;
    ArrayList<String> list;
    HashMap<File,String> fileNameList;

    // gestion du menu (voir main activity for details)
    private String[] mMenuItem;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private LinearLayout point;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fileNameList = new HashMap<File, String>();
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
        View myView = factory.inflate(R.layout.collection_showcase_layout, null);
        point = (LinearLayout) findViewById(R.id.point);
        point.addView(myView);

        //list = imageReader(new File("sdcard/EkioPhotos"));

        //new
        CollectionableDAO objectDao = new CollectionableDAO(this);
        objectDao.open();
        list = objectDao.allPath();
        objectDao.close();

        gv = (GridView) findViewById(R.id.gridView);
        gv.setAdapter(new GridAdapter());
        context = this;
        gv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CollectionableDAO objectDao2 = new CollectionableDAO(context);
                objectDao2.open();
                int idObject = objectDao2.getIdFromPath(list.get(position));
                objectDao2.close();
                Intent intent = new Intent(context, ChooseFromCollectionActivity.class);
                intent.putExtra(MESSAGE_KEY, idObject);
                startActivity(intent);
            }
        });


        }
    class GridAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.single_grid , parent ,false);
            ImageView iv = (ImageView) convertView.findViewById(R.id.single_slot);
            //iv.setImageURI(Uri.parse(getItem(position).toString()));
            Uri selectedImage = Uri.parse("file:///"+getItem(position).toString());
            try {
                iv.setImageBitmap(decodeUri(selectedImage));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return convertView;
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

    }


/*    ArrayList<File> imageReader(File root){
        ArrayList<File> collection = new ArrayList<File>();

        File[] files = root.listFiles();

        for(int i = 0; i < files.length; i++){
            if (files[i].isDirectory()){
                collection.addAll(imageReader(files[i]));
            }
            else {
                if (files[i].getName().endsWith(".jpg")){
                    collection.add(files[i]);
                    fileNameList.put(files[i], files[i].getAbsolutePath());
                }
            }
        }

        return collection;
    }*/

}
