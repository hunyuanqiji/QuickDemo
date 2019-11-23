/*
 *
 *  MIT License
 *
 *  Copyright (c) 2017 Alibaba Group
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 */
package cn.plugin.core.ui.attachment.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.plugin.core.R;
import cn.plugin.core.ui.attachment.bean.AttachmentEntity;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by mikeafc on 15/11/26.
 */
public class AttachmentDownloadPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<AttachmentEntity> mDatas;

    public AttachmentDownloadPagerAdapter(Context context, List<AttachmentEntity> list) {
        this.mContext = context;
        this.mDatas = list;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_attach_download_pager, null);

        final PhotoView file_image_iv = view.findViewById(R.id.file_image_iv);
        ImageView file_icon_iv = view.findViewById(R.id.file_icon_iv);
        TextView file_name_tv = view.findViewById(R.id.file_name_tv);

        AttachmentEntity entity = mDatas.get(position);
        if (!TextUtils.isEmpty(entity.getFilePath())) {
            Glide.with(mContext).load(entity.getFilePath()).into(file_image_iv);
        }

        file_image_iv.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                if (onClickPhotoViewListener != null){
                    onClickPhotoViewListener.onClickPhotoView();
                }
            }

            @Override
            public void onOutsidePhotoTap() {
                if (onClickPhotoViewListener != null){
                    onClickPhotoViewListener.onClickPhotoView();
                }
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    private OnClickPhotoViewListener onClickPhotoViewListener;

    public interface OnClickPhotoViewListener {
        void onClickPhotoView();
    }

    public void setOnClickPhotoViewListener(OnClickPhotoViewListener onClickPhotoViewListener) {
        this.onClickPhotoViewListener = onClickPhotoViewListener;
    }
}
