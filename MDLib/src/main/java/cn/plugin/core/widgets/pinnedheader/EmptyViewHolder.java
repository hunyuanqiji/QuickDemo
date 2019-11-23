package cn.plugin.core.widgets.pinnedheader;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.plugin.core.R;
import cn.plugin.core.widgets.EmptyLayout;

/**
 * Created by yy on 2019/10/11.
 **/
public class EmptyViewHolder extends RecyclerView.ViewHolder {
    public EmptyLayout emptyLayout;

    public EmptyViewHolder(View itemView) {
        super(itemView);
        emptyLayout = itemView.findViewById(R.id.EmptyLayout);
    }
}
