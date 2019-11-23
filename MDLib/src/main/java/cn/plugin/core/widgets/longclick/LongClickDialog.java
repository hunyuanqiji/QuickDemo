package cn.plugin.core.widgets.longclick;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.Arrays;
import java.util.List;

import cn.plugin.core.R;
import cn.plugin.core.utils.DividerItemDecoration;
import cn.plugin.core.widgets.longclick.adapter.LongClickAdapter;


/**
 * 长按提示框，单选
 * Created by NJQ on 2018/6/26.
 */
public class LongClickDialog extends Dialog {
    private Context context;
    private RecyclerView rv;
    private LongClickAdapter mAdapter;

    public LongClickDialog(@NonNull Context context) {
        this(context, R.style.NoDialogTitleView);
    }

    public LongClickDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context,themeResId);
        this.context = context;
        setContentView(R.layout.dialog_long_click_layout);

        rv = findViewById(R.id.rv);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL_LIST, R.drawable.list_divider);
        rv.addItemDecoration(dividerItemDecoration);
        rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mAdapter = new LongClickAdapter();
        rv.setAdapter(mAdapter);

        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;//设置dialog 在布局中的位置
        setCanceledOnTouchOutside(true);// 设置点击屏幕Dialog不消失

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (onItemClickedListener != null){
                    onItemClickedListener.onClicked(position);
                }
                dismiss();
            }
        });
    }

    public void setData(String[] strs){
        if (strs != null && strs.length > 0) {
            mAdapter.setNewData(Arrays.asList(strs));
        }
    }

    public void setData(List<String> list){
        if (list != null && list.size() > 0) {
            mAdapter.setNewData(list);
        }
    }

    @Override
    public void show() {
        super.show();
        setParams(0.7);
    }

    private void setParams(double rote) {
        Window window = this.getWindow();
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.widthPixels * rote); // 宽度设置为屏幕的0.65
        window.setAttributes(p);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    public interface OnItemClickedListener {
        void onClicked(int position);
    }
}
