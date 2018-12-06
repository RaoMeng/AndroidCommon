package com.raomeng.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @author RaoMeng
 * @describe 多fragment嵌套Viewpager适配器
 * 页面不可见时会被销毁并释放资源，适用于页面数量较多的情况，不会占用大量内存
 * @date 2018/1/15 11:02
 */

public class TabFragmentStateAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public TabFragmentStateAdapter(List<Fragment> fragments, List<String> titles, FragmentManager fm) {
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
