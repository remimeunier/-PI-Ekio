package com.example.remi.ekio;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class CollectionShowcaseActivity extends Activity {

    private GridView gv;
    ArrayList<File> list;
    HashMap<File,String> fileNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fileNameList = new HashMap<File, String>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_showcase_layout);
        list = imageReader(new File("sdcard/EkioPhotos"));
        gv = (GridView) findViewById(R.id.gridView);
        gv.setAdapter(new GridAdapter());
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), PhotoGrandEcranACtivity.class)
                        .putExtra("img", list.get(position).toString()));
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
            iv.setImageURI(Uri.parse(getItem(position).toString()));



            return convertView;
        }
    }


    ArrayList<File> imageReader(File root){
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
    }

    public void goGrandEcran (View view){
        //TODO
    }
}
