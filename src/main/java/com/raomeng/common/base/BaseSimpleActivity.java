package com.raomeng.common.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.raomeng.common.R;
import com.raomeng.common.rx.RxManager;

/**
 * @author RaoMeng
 * @describe 非MVP模式Activity基类
 * @date 2018/1/5 17:55
 */

public abstract class BaseSimpleActivity extends AppCompatActivity {

    protected RxManager mRxManager;

    /**
     * 通用的ToolBar标题
     */
    private TextView mTitleTextView;

    /**
     * 通用的ToolBar
     */
    private Toolbar mToolbar;

    /**
     * 内容区域
     */
    private RelativeLayout mContentLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBeforeSetContentView();

        setContentView(R.layout.activity_base);

        initToolBar();

        setContentLayout(getLayout());

        initView();

        initEvent();

        initData();
    }

    private void initBeforeSetContentView() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            throw new IllegalStateException("can not exist Actionbar");
        }
        if (mRxManager == null) {
            mRxManager = new RxManager();
        }
    }

    private void initToolBar() {
        mTitleTextView = (TextView) findViewById(R.id.activity_base_title_tv);
        mToolbar = (Toolbar) findViewById(R.id.activity_base_toolbar);
        mContentLayout = (RelativeLayout) findViewById(R.id.activity_base_content_rl);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setBackArrow();
    }

    protected abstract int getLayout();

    protected abstract void initView();

    protected abstract void initEvent();

    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String simpleName = this.getClass().getSimpleName();
        mRxManager.unSubscribe(simpleName);
    }

    @Override
    public void onBackPressed() {
        if (onBackEvent()) {
            return;
        }
        super.onBackPressed();
    }

    /**
     * 设置toolbar下面内容区域的内容
     *
     * @param layoutId
     */
    private void setContentLayout(int layoutId) throws Resources.NotFoundException {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(layoutId, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mContentLayout.addView(contentView, params);
    }

    /**
     * 子类调用，重新设置Toolbar
     *
     * @param toolBar
     */
    protected void setToolBar(Toolbar toolBar) {
        hidetoolBar();
        setSupportActionBar(toolBar);
        //设置actionBar的标题是否显示，对应ActionBar.DISPLAY_SHOW_TITLE。
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /**
     * 隐藏ToolBar，通过setToolBar重新定制ToolBar
     */
    protected void hidetoolBar() {
        mToolbar.setVisibility(View.GONE);
    }

    /**
     * ToolBar菜单的点击事件
     *
     * @param onclick
     */
    protected void setToolBarMenuSelectedListener(Toolbar.OnMenuItemClickListener onclick) {
        mToolbar.setOnMenuItemClickListener(onclick);
    }

    /**
     * 设置左上角back按钮
     */
    private void setBackArrow() {
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_back);
        //给ToolBar设置左侧的图标
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        // 给左上角图标的左边加上一个返回的图标 。对应ActionBar.DISPLAY_HOME_AS_UP
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置返回按钮的点击事件
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBackEvent()) {
                    return;
                }
                finish();
            }
        });
    }

    protected void hideBackArrow() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    /**
     * 设置标题
     *
     * @param title
     */
    protected void setActionTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTitleTextView.setText(title);
        }
    }

    /**
     * 设置标题
     *
     * @param resId
     */
    protected void setActionTitle(int resId) throws Resources.NotFoundException {
        mTitleTextView.setText(resId);
    }

    protected boolean onBackEvent() {
        return false;
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    protected void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    protected void toast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show();
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    protected void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    protected void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
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
    protected void startActivityForResult(Class<?> cls, Bundle bundle,
                                          int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
}
