package com.prashant.shoppinglist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

/**
 * Created by Prashant on 2/9/2018.
 */

//this is helper class for TabListActivity

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        if(position==1)
        {
            UploadTab ut=new UploadTab();
            return ut;
        }
        else
        {
            SmartListTab sm=new SmartListTab();
            return sm;
        }

    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Smart List";
            case 1:
                return "Upload";

        }
        return null;
    }
}
