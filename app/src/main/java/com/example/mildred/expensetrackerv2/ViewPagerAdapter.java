package com.example.mildred.expensetrackerv2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.mildred.expensetrackerv2.BudgetFragment.BudgetFragment;
import com.example.mildred.expensetrackerv2.HomeFragment.HomeFragment;
import com.example.mildred.expensetrackerv2.SettingsFragment.SettingsFragment;
import com.example.mildred.expensetrackerv2.WishFragment.WishFragment;

/**
 * Created by mildred on 03/05/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    private Fragment currentFragment = new Fragment();

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String faneTitle = null;
        switch (position) {
            case 0:
                faneTitle = "Home";
                break;
            case 1:
                faneTitle = "Budget";
                break;
            case 2:
                faneTitle = "Wish";
                break;
            case 3:
                faneTitle = "Settings";
                break;
            default:
                faneTitle = "Home";
                break;
        }

        return faneTitle;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = null;

        switch(position)
        {
            case 0: f = new HomeFragment();
                break;
            case 1: f = new BudgetFragment();

                break;
            case 2: f=  new WishFragment();
                break;
            case 3: f = new SettingsFragment();
                break;
            default:
                f= new HomeFragment();
                break;
        }


        Bundle b = new Bundle();
        b.putInt("position", position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if(object!=null)
        currentFragment = (Fragment) object;
    }

    public Fragment getPrimaryItem()
    {
        if(currentFragment!=null)
            return currentFragment;

        return currentFragment;
    }
}
