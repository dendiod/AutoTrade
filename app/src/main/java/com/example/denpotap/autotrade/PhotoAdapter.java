package com.example.denpotap.autotrade;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PhotoAdapter extends FragmentPagerAdapter {
    public PhotoAdapter(FragmentManager mgr) {
        super(mgr);
    }
    @Override
    public int getCount() {
        if(DetailsActivity.activity.equals("main"))
            return(MyArrays.arrays[MainActivity.cars.get(MainActivity.index).getIndex()].length);
        else
            return(MyArrays.arrays[FavouritesActivity.cars.get(MainActivity.index).getIndex()].length);
    }
    @Override
    public Fragment getItem(int position) {
        return(PhotoFragment.newInstance(position));
    }
}
