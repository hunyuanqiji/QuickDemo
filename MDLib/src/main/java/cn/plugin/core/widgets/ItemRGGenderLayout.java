package cn.plugin.core.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cn.plugin.core.R;


/**
 * 项目名称：v1.1.2
 * 类描述：带RadioGroup的选择男女条目
 * Created by NJQ on 2017/3/29
 */

public class ItemRGGenderLayout extends LinearLayout {
    private static final String TAG = "ItemRGGenderLayout";

    public static final String[] GENDER_ARR = new String[]{"男", "女", "暂无"};
    public static final String[] GENDER_CODE = new String[]{"10000855", "10000856", "10000857"};

    private Context mContext;
    private RadioGroup rg;
    private RadioButton rbMan, rbWoman;
    private String mCode = GENDER_CODE[2];         // 默认暂无

    public ItemRGGenderLayout(Context context) {
        this(context, null);
    }

    public ItemRGGenderLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemRGGenderLayout(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View.inflate(context, R.layout.item_rg_layout, this);

        rg = findViewById(R.id.rg);
        rbMan = findViewById(R.id.rb_man);
        rbWoman = findViewById(R.id.rb_woman);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                resetStatus();
                if (checkedId == R.id.rb_man) {
                    rbMan.setTextColor(mContext.getResources().getColor(R.color.white));
                    rbMan.setChecked(true);
                    mCode = GENDER_CODE[0];
                    if (onCheckedChangeListener != null) {
                        onCheckedChangeListener.onCheckedChanged(mCode);
                    }

                } else if (checkedId == R.id.rb_woman) {
                    rbWoman.setTextColor(mContext.getResources().getColor(R.color.white));
                    rbWoman.setChecked(true);
                    mCode = GENDER_CODE[1];
                    if (onCheckedChangeListener != null) {
                        onCheckedChangeListener.onCheckedChanged(mCode);
                    }

                }
            }
        });
    }

    private void resetStatus() {
        rbMan.setTextColor(mContext.getResources().getColor(R.color.item_title_TextColor_light));
        rbMan.setChecked(false);
        rbWoman.setTextColor(mContext.getResources().getColor(R.color.item_title_TextColor_light));
        rbWoman.setChecked(false);
    }

    public void setCode(String code) {
        this.mCode = code;
        resetStatus();
        switch (code) {
            case "10000855":
                rbMan.setTextColor(mContext.getResources().getColor(R.color.white));
                rbMan.setChecked(true);
                break;
            case "10000856":
                rbWoman.setTextColor(mContext.getResources().getColor(R.color.white));
                rbWoman.setChecked(true);
                break;
            case "10000857":
                break;
        }
    }

    public String getCode() {
        return mCode;
    }

    private OnCheckedChangeListener onCheckedChangeListener;

    public interface OnCheckedChangeListener{
        void onCheckedChanged(String code);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener){
        this.onCheckedChangeListener = listener;
    }
}
