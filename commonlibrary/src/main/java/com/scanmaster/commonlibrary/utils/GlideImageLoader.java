package com.scanmaster.commonlibrary.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.scanmaster.commonlibrary.R;
import com.youth.banner.loader.ImageLoader;

/**
 * author:  zhouchaoxiang
 * date:    2018/9/28
 * explain:
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context)
                .load((String) path)
                .apply(new RequestOptions().centerCrop().error(R.drawable.ico_bg))
                .into(imageView);
    }
}
