package cn.plugin.core.widgets;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import cn.plugin.core.utils.DividerItemDecoration;

/**
 * Created by asmin on 2017/4/7.
 */

public class SingleDialog extends Dialog implements View.OnClickListener, DialogInterface.OnDismissListener {
    private static final String TAG = "TipsDialog";
    private Context context;
    private RecyclerView mRV;
    private TextView mTVcancle, mTVsure;
    private View mLayoutBtn;
    private List<String> list = new ArrayList<>();
    private List<SingleBean> singleLists = new ArrayList<>();
    private List<Boolean> isSelected = new ArrayList<>();
    private SingleDialogAdapter mAdapter;
    private SingleBean selectedSingleBean;
    private OnCustomViewOnClickListener onCustomViewOnClickListener;
    private int pos;
    private boolean noDismiss = false;

    public void setNoDismiss(boolean noDismiss) {
        this.noDismiss = noDismiss;
    }

    public interface OnClickSureListener {
        void onClick(int position);
    }

    private OnClickSureListener onClickSureListener;

    public void setOnClickSureListener(OnClickSureListener onClickSureListener) {
        this.onClickSureListener = onClickSureListener;
    }

    public void setOnCustomViewOnClickListener(OnCustomViewOnClickListener onCustomViewOnClickListener) {
        this.onCustomViewOnClickListener = onCustomViewOnClickListener;
    }

    public SingleDialog(Context context) {
        super(context, R.style.NoDialogTitleView);
        this.context = context;
        setCanceledOnTouchOutside(true);// 设置点击屏幕Dialog不消失
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_choose_dialog_layout);
        setOnDismissListener(this);
        mAdapter = new SingleDialogAdapter();
        initViews();
        setListener();
    }

    private void setListener() {
        mTVcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mTVsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickSureListener != null) {
                    onClickSureListener.onClick(pos);
                }
                if (isShowing()) {
                    dismiss();
                }
            }
        });

        mRV.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL_LIST, R.drawable.list_divider,
                context.getResources().getDimensionPixelSize(R.dimen.divider_paddingLeft),
                context.getResources().getDimensionPixelSize(R.dimen.divider_paddingRight)));
        mRV.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mRV.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mAdapter.setOnListItemClickListener(new OnListItemClickListener() {
            @Override
            public void onListItemClick(int position) {
                for (SingleBean bean :
                        singleLists) {
                    bean.setCheck(false);
                }
                mAdapter.getItem(position).setCheck(true);
                selectedSingleBean = mAdapter.getItem(position);
                mAdapter.notifyDataSetChanged();
                pos = position;
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                msg.setData(bundle);
                mHanlder.sendMessageDelayed(msg, 100);
            }
        });
    }

    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int position = msg.getData().getInt("position");
            if (onCustomViewOnClickListener != null) {
                onCustomViewOnClickListener.onClick(position);
            }
            if (isShowing() && !noDismiss) {
                dismiss();
            }
        }
    };


    public void setDefault(String defaultStr) {
        for (SingleBean bean :
                singleLists) {
            if (bean.getTitle().equals(defaultStr)) {
                bean.setCheck(true);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public void setDefault(int defaultPos) {
      /*  for (LabelBean bean :
                singleLists) {
            if (bean.getTitle().equals(defaultStr)){
                bean.setCheck(true);
            }
        }*/
        singleLists.get(defaultPos).setCheck(true);
        mAdapter.notifyDataSetChanged();
    }

    private void initViews() {
        mRV = findViewById(R.id.single_choose_dialog_layout_RV);
        mTVcancle = findViewById(R.id.single_choose_dialog_layout_TV_cancle);
        mTVsure = findViewById(R.id.single_choose_dialog_layout_TV_sure);
        mLayoutBtn = findViewById(R.id.single_choose_dialog_layout_Layout);
    }

    @Override
    public void show() {
        super.show();
        mLayoutBtn.setVisibility(View.GONE);
        setParams(0.75);
    }

    public void show(String[] list, boolean showButton) {
        super.show();
        mLayoutBtn.setVisibility(showButton ? View.VISIBLE : View.GONE);

        for (int i = 0; i < list.length; i++) {
            SingleBean singleBean = new SingleBean(list[i]);
            singleLists.add(singleBean);
        }

        mAdapter.notifyDataSetChanged();
        setParams(0.75, 0.8);
    }


    public void show(String[] list) {
        this.show(list, false);
    }

    public void show(List<String> list) {
        super.show();
        mLayoutBtn.setVisibility(View.GONE);
//        setParamsDefault();
        for (int i = 0; i < list.size(); i++) {
            SingleBean singleBean = new SingleBean(list.get(i));
            singleLists.add(singleBean);
        }
        mAdapter.notifyDataSetChanged();
        setParams(0.75, 0.8);
    }

    public void show(List<String> list, boolean showButton) {
        super.show();
        mLayoutBtn.setVisibility(showButton ? View.VISIBLE : View.GONE);
        for (int i = 0; i < list.size(); i++) {
            SingleBean singleBean = new SingleBean(list.get(i));
            singleLists.add(singleBean);
        }
        mAdapter.notifyDataSetChanged();
        setParams(0.75, 0.8);
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
        int allHeight = 100 * mAdapter.getItemCount();
        if (((allHeight * 1.0) / d.heightPixels) > 0.7) {
            p.height = (int) (d.heightPixels * heightRote); // 高度设置为屏幕的0.8
        }

        if (mAdapter.getItemCount() > 7) {
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
            final SingleDialogViewHolder holder = new SingleDialogViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_dialog_layout, parent, false));
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
                mTV = itemView.findViewById(R.id.item_single_dialog_layout_TV_title);
                mCB = itemView.findViewById(R.id.item_single_dialog_layout_TV_CB);
                viewRoot = itemView.findViewById(R.id.item_single_dialog_layout_Layout_root);
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