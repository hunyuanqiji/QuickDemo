package cn.plugin.core.widgets;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.plugin.core.R;
import cn.plugin.core.listener.OnCustomViewOnClickListener;
import cn.plugin.core.listener.OnListItemClickListener;
import cn.plugin.core.listener.OnMultiListClickListener;
import cn.plugin.core.utils.DividerItemDecoration;

/**
 * 多选框
 * Created by asmin on 2017/4/7.
 */

public class MultiCheckDialog extends AlertDialog implements View.OnClickListener, DialogInterface.OnDismissListener {
    private static final String TAG = "TipsDialog";
    private Context context;
    private RecyclerView mRV;
    private TextView mTVcancle, mTVsure;
    private List<String> list = new ArrayList<>();
    private List<SingleBean> singleLists = new ArrayList<>();
    private List<Boolean> isSelected = new ArrayList<>();
    private SingleDialogAdapter mAdapter;
    private SingleBean selectedSingleBean;
    private OnCustomViewOnClickListener onCustomViewOnClickListener;
    private OnMultiListClickListener onMultiListClickListener;

    public void setOnMultiListClickListener(OnMultiListClickListener onMultiListClickListener) {
        this.onMultiListClickListener = onMultiListClickListener;
    }

    public void setOnCustomViewOnClickListener(OnCustomViewOnClickListener onCustomViewOnClickListener) {
        this.onCustomViewOnClickListener = onCustomViewOnClickListener;
    }

    public MultiCheckDialog(Context context) {
        super(context, R.style.NoDialogTitleView);
        this.context = context;
        setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_dialog_layout);
        setOnDismissListener(this);
        mAdapter = new SingleDialogAdapter();
        initViews();
        setListener();
    }

    private void setListener() {
        mRV.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL_LIST, R.drawable.list_divider,
                context.getResources().getDimensionPixelSize(R.dimen.divider_paddingLeft),
                context.getResources().getDimensionPixelSize(R.dimen.divider_paddingRight)));
        mRV.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mRV.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mAdapter.setOnListItemClickListener(new OnListItemClickListener() {
            @Override
            public void onListItemClick(int position) {
                mAdapter.getItem(position).setCheck(!mAdapter.getItem(position).isCheck);
                mAdapter.notifyItemChanged(position);
                if (onMultiListClickListener != null) {
                    onMultiListClickListener.onClick(position, mAdapter.getItem(position).isCheck());
                }
            }
        });

        mTVcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mTVsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCustomViewOnClickListener != null) {
                    onCustomViewOnClickListener.onClick(0);
                }
            }
        });
    }

    public void setDefault(boolean[] state) {
        for (int i = 0; i < state.length; i++) {
            if (state[i] == true) {
                mAdapter.getItem(i).setCheck(true);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initViews() {
        mRV = findViewById(R.id.multi_dialog_layout_RV);
        mTVcancle = findViewById(R.id.multi_dialog_layout_TV_cancle);
        mTVsure = findViewById(R.id.multi_dialog_layout_TV_sure);
    }

    @Override
    public void show() {
        super.show();
        setParams(0.75);
    }

    public void show(String[] list) {
        super.show();
//        setParamsDefault();
        for (int i = 0; i < list.length; i++) {
            SingleBean singleBean = new SingleBean(list[i]);
            singleLists.add(singleBean);
        }
        mAdapter.notifyDataSetChanged();
//        setParams(0.75);
        setParams(0.75, 0.8);
    }

    public void show(List<String> list) {
        super.show();
//        setParamsDefault();
        for (int i = 0; i < list.size(); i++) {
            SingleBean singleBean = new SingleBean(list.get(i));
            singleLists.add(singleBean);
        }
        mAdapter.notifyDataSetChanged();
        setParams(0.75, 0.7);
    }

    private void setParams(double rote) {
        Window window = this.getWindow();
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.widthPixels * rote); // 宽度设置为屏幕的0.75
        window.setAttributes(p);
    }

    private void setParams(double widthRote, double heightRote) {
        Window window = this.getWindow();
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.widthPixels * widthRote); // 宽度设置为屏幕的0.75
        int allHeight = 100 * mAdapter.getItemCount() - 90;
        if (((allHeight * 1.0) / d.heightPixels) > 0.7) {
            p.height = (int) (d.heightPixels * heightRote); // 高度设置为屏幕的0.8
        }
        window.setAttributes(p);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            this.dismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public String getSelectd() {
        if (selectedSingleBean != null) {
            return selectedSingleBean.getTitle();
        }
        return "";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        singleLists.clear();
    }

    private class SingleDialogAdapter extends RecyclerView.Adapter<SingleDialogAdapter.SingleDialogViewHolder> {
        @Override
        public SingleDialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final SingleDialogViewHolder holder = new SingleDialogViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_multi_dialog_layout, parent, false));
            return holder;
        }

        public SingleBean getItem(int position) {
            return singleLists.get(position);
        }

        @Override
        public void onBindViewHolder(final SingleDialogViewHolder holder, final int position) {
            singleLists.get(position).setPosition(position);
            holder.mTV.setText(singleLists.get(position).getTitle());
            holder.mCB.setChecked(getItem(position).isCheck);
            holder.viewRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onListItemClickListener != null) {
                        onListItemClickListener.onListItemClick(position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return singleLists == null ? 0 : singleLists.size();
        }

        protected class SingleDialogViewHolder extends RecyclerView.ViewHolder {
            private TextView mTV;
            private CheckBox mCB;
            private View viewRoot;

            public SingleDialogViewHolder(View itemView) {
                super(itemView);
                mTV = itemView.findViewById(R.id.item_multi_dialog_layout_TV_title);
                mCB = itemView.findViewById(R.id.item_multi_dialog_layout_TV_CB);
                viewRoot = itemView.findViewById(R.id.item_multi_dialog_layout_Layout_root);
            }
        }

        private OnListItemClickListener onListItemClickListener;

        public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener) {
            this.onListItemClickListener = onListItemClickListener;
        }

    }

    private class SingleBean {
        private String title;
        private boolean isCheck;
        private int position;

        public SingleBean() {
        }

        public SingleBean(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}