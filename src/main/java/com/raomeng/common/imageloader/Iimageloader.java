package com.raomeng.common.imageloader;

import android.content.Context;
import android.widget.ImageView;

import java.io.File;

/**
 * @author RaoMeng
 * @describe 图片加载接口
 * @date 2018/2/6 10:14
 */

public interface Iimageloader {

    void display(Context context, String url, ImageView imageView);
    void display(Context context, String url, ImageView imageView, int placeholder, int error);
    void displayWithCookie(Context context, String url, ImageView imageView);
    void displayWithCookie(Context context, String url, ImageView imageView, int placeholder, int error);

    void display(Context context, File file, ImageView imageView);
    void display(Context context, File file, ImageView imageView, int placeholder, int error);
    void displayWithCookie(Context context, File file, ImageView imageView);
    void displayWithCookie(Context context, File file, ImageView imageView, int placeholder, int error);

    void displayRound(Context context, File file, ImageView imageView);
    void displayRound(Context context, File file, ImageView imageView, int placeholder, int error);
    void displayRoundWithCookie(Context context, File file, ImageView imageView);
    void displayRoundWithCookie(Context context, File file, ImageView imageView, int placeholder, int error);

    void displayCircle(Context context, File file, ImageView imageView);
    void displayCircle(Context context, File file, ImageView imageView, int placeholder, int error);
    void displayCircleWithCookie(Context context, File file, ImageView imageView);
    void displayCircleWithCookie(Context context, File file, ImageView imageView, int placeholder, int error);

    void displaySmall(Context context, File file, ImageView imageView);
    void displaySmall(Context context, File file, ImageView imageView, int placeholder, int error);
    void displaySmallWithCookie(Context context, File file, ImageView imageView);
    void displaySmallWithCookie(Context context, File file, ImageView imageView, int placeholder, int error);

    void displayBig(Context context, File file, ImageView imageView);
    void displayBig(Context context, File file, ImageView imageView, int placeholder, int error);
    void displayBigWithCookie(Context context, File file, ImageView imageView);
    void displayBigWithCookie(Context context, File file, ImageView imageView, int placeholder, int error);

}
