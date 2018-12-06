package com.raomeng.common.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.raomeng.common.rx.RxManager;

/**
 * @author RaoMeng
 * @describe 非MVP模式fragment基类
 * @date 2018/1/8 11:00
 */

public abstract class BaseSimpleFragment extends Fragment {
    protected Context mContext;
    protected Bundle mBundle;
    protected RxManager mRxManager;

    /**
     * 是否是第一次加载
     */
    protected boolean isFirstLoad = true;

    /**
     * fragment视图View
     */
    private View mRootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mBundle = savedInstanceState.getBundle("fragment_bundle");
        } else {
            mBundle = getArguments() == null ? new Bundle() : getArguments();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            isFirstLoad = false;
        } else {
            mRootView = inflater.inflate(getLayout(), container, false);
        }
        if (mRxManager == null) {
            mRxManager = new RxManager();
        }
        return mRootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
        initEvents();
        initDatas();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mBundle != null) {
            outState.putBundle("fragment_bundle", mBundle);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRxManager.unSubscribe(this.getClass().getSimpleName());
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T $(int resId) {
        if (mRootView == null) {
            throw new NullPointerException("rootView is null");
        }
        return (T) mRootView.findViewById(resId);
    }

    /**
     * fragment进行回退
     */
    protected void onBack() {
        getFragmentManager().popBackStack();
    }

    protected void toast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
    }

    protected void toast(int resId) {
        Toast.makeText(mContext, resId, Toast.LENGTH_LONG).show();
    }

    /**
     * 跳转fragment
     *
     * @param tofragment
     */
    public void startFragment(Fragment tofragment) {
        startFragment(tofragment, null);
    }

    /**
     * @param tofragment 跳转的fragment
     * @param tag        fragment的标签
     */
    public void startFragment(Fragment tofragment, String tag) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.hide(this).add(android.R.id.content, tofragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mContext, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 隐式跳转
     *
     * @param action
     */
    public void startActivity(String action) {
        startActivity(action, null);
    }

    public void startActivity(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(String action, int requestCode) {
        startActivityForResult(action, null, requestCode);
    }

    public void startActivityForResult(String action, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    protected abstract int getLayout();

    protected abstract void initViews();

    protected abstract void initEvents();

    protected abstract void initDatas();
}
