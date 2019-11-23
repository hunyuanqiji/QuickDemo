package cn.plugin.core.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Went_Gone on 2017/9/12.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> listFragments;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> al) {
        super(fm);
        listFragments = al;
    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return listFragments.get(position);
    }

    @Override
    public int getCount() {
        return listFragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
