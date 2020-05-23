package com.example.news2;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CategoryAdapter extends FragmentPagerAdapter {

    //context
    private Context mContext;

    public CategoryAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HeadlinesFragment();
            case 1:
                return new SportsFragment();
            case 2:
                return new EntertainmentFragment();
            case 3:
                return new GlobalFragment();
            case 4:
                return new BusinessFragment();
            case 5:
                return new HealthFragment();
            case 6:
                return new PoliticsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return mContext.getString(R.string.headlines);
            case 1:
                return mContext.getString(R.string.sports);
            case 2:
                return mContext.getString(R.string.entertainment);
            case 3:
                return mContext.getString(R.string.global);
            case 4:
                return mContext.getString(R.string.business);
            case 5:
                return mContext.getString(R.string.health);
            case 6:
                return mContext.getString(R.string.politics);
            default:
                return null;
        }
    }
}
