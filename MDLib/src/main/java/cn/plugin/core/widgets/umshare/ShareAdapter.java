package cn.plugin.core.widgets.umshare;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.plugin.core.R;
import cn.plugin.core.bean.ShareBean;

/**
 * Created by yy on 2019/4/18.
 **/
public class ShareAdapter extends BaseQuickAdapter<ShareBean, BaseViewHolder> {

    public ShareAdapter() {
        super(R.layout.item_um_share);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ShareBean item) {
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_name, item.getName());
        }
        helper.setImageResource(R.id.iv_icon, item.getIconRes());
    }
}