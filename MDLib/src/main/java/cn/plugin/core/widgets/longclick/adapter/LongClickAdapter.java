package cn.plugin.core.widgets.longclick.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.plugin.core.R;


public class LongClickAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public LongClickAdapter() {
        super(R.layout.item_long_click);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv, item);
    }
}
