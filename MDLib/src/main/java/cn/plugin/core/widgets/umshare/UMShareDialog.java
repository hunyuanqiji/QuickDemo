package cn.plugin.core.widgets.umshare;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.plugin.core.R;
import cn.plugin.core.bean.ShareBean;
import cn.plugin.core.utils.DensityUtil;
import cn.plugin.core.utils.SpaceItemDecoration;
import cn.plugin.core.widgets.wheelview.OnDateTimeClickSureListener;
import cn.plugin.core.widgets.wheelview.WheelView;
import cn.plugin.core.widgets.wheelview.util.DateUtils;


/**
 * 友盟分享Dialog
 * Created by NJQ on 2018/7/11.
 */
public class UMShareDialog extends Dialog {
    private static final String TAG = "UMShareDialog";

    private Context mContext;
    private RecyclerView rvShare;

    private List<ShareBean> shareBeans = new ArrayList<>();

    public UMShareDialog(@NonNull Context context) {
        this(context, R.style.BottomDialogStyle);
    }

    public UMShareDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 获取Window的LayoutParams
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = ViewGroup.LayoutParams.MATCH_PARENT;
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);
        setContentView(R.layout.dialog_um_share);

        initView();
    }

    private void initView() {
        rvShare = findViewById(R.id.rv_share);
        rvShare.addItemDecoration(new SpaceItemDecoration(3, DensityUtil.dip2px(mContext, 10)));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false);
        rvShare.setLayoutManager(gridLayoutManager);

        ShareBean bean0 = new ShareBean();
        bean0.setName("微信");
        bean0.setIconRes(R.drawable.ic_weixin);
        bean0.setType(ShareBean.TYPE_WEIXIN);
        ShareBean bean1 = new ShareBean();
        bean1.setName("朋友圈");
        bean1.setIconRes(R.drawable.ic_weixin_circle);
        bean0.setType(ShareBean.TYPE_WEIXIN_CIRCLE);
        ShareBean bean2 = new ShareBean();
        bean2.setName("微博");
        bean2.setIconRes(R.drawable.ic_weibo);
        bean0.setType(ShareBean.TYPE_WEIBO);
        shareBeans.add(bean0);
        shareBeans.add(bean1);
        shareBeans.add(bean2);

        ShareAdapter mAdapter = new ShareAdapter();
        rvShare.setAdapter(mAdapter);
        mAdapter.setNewData(shareBeans);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case ShareBean.TYPE_WEIXIN:
                        if (onShareClickListener != null){
                            onShareClickListener.onShareClicked(SHARE_MEDIA.WEIXIN);
                        }
                        break;
                    case ShareBean.TYPE_WEIXIN_CIRCLE:
                        if (onShareClickListener != null){
                            onShareClickListener.onShareClicked(SHARE_MEDIA.WEIXIN_CIRCLE);
                        }
                        break;
                    case ShareBean.TYPE_WEIBO:
                        if (onShareClickListener != null){
                            onShareClickListener.onShareClicked(SHARE_MEDIA.SINA);
                        }
                        break;
                }
            }
        });
    }

    private OnShareClickListener onShareClickListener;

    public void setOnShareClickListener(OnShareClickListener onShareClickListener) {
        this.onShareClickListener = onShareClickListener;
    }

    public interface OnShareClickListener{
        void onShareClicked(SHARE_MEDIA shareMedia);
    }
}
