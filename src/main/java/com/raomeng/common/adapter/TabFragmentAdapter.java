package com.raomeng.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author RaoMeng
 * @describe 多fragment嵌套Viewpager适配器
 * 适用于那些相对静态的页，数量也比较少的情况
 * @date 2018/1/15 11:02
 */

public class TabFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public TabFragmentAdapter(List<Fragment> fragments, List<String> titles, FragmentManager fm) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles == null || mTitles.size() <= position) {
            return "";
        }
        return mTitles.get(position);
    }
}
