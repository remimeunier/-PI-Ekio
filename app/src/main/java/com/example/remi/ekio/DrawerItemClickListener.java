package com.example.remi.ekio;


import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.content.Intent;
import android.content.Context;


class DrawerItemClickListener implements ListView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        //selectItem(position);
        Context context = view.getContext();
        switch (position) {
            case 0:
                Intent newActivity = new Intent(context, BeforePictureActivity.class);
                context.startActivity(newActivity);
                break;
            case 1:
                Intent newActivity2 = new Intent(context, CollectionShowcaseActivity.class);
                context.startActivity(newActivity2);
                break;
            case 2:
                Intent intent = new Intent(context, PhotoGrandEcranACtivity.class);
                intent.putExtra("com.example.remi.ekio.messagekey", 1);
                context.startActivity(intent);
                break;
        }
    }

    //** Swaps fragments in the main content view *//

  /*  private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        // mTitle = title;
        // getActionBar().setTitle(mTitle);
    }*/
}
