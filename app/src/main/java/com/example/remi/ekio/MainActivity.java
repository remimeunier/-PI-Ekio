package com.example.remi.ekio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import android.widget.AdapterView;

public class MainActivity extends AppCompatActivity {

    private String[] mMenuItem;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recup la list des menu item
        mMenuItem = getResources().getStringArray(R.array.menu_item);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        // Set the adapter for the list view
       // mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.menu_item, R.id.Itemname, mMenuItem));
        // custom adapater
        CustomListAdapter adapter=new CustomListAdapter(this, mMenuItem);
        mDrawerList.setAdapter(adapter);

        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
       // mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

    }
}
