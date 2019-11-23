package cn.plugin.core.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.plugin.core.R;
import cn.plugin.core.utils.DensityUtil;
import cn.plugin.core.utils.ToastUtil;

/**
 * Created by Yang_Yang on 2019/7/22 0022
 */
public class RecordItemETRightLayoutextends extends RelativeLayout {
    private static final String TAG = "InfoItemETLayout";
    private TextView mTVtitle, mTVimportent, mLable;
    private EditText mET;
    private String title;
    private String hint;
    private int input_type = 0;
    private boolean importent;

    private static final int STR = 0;
    private static final int PHONE = 1;
    private static final int USERNAME = 2;
    private static final int NUMBER = 3;
    private static final int PASSWORD = 4;
    private static final int ID_CARD = 5;
    String text = "";
    private Context mContext;

    public RecordItemETRightLayoutextends(Context context) {
        this(context, null);
    }

    public RecordItemETRightLayoutextends(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecordItemETRightLayoutextends(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View.inflate(context, R.layout.record_item_et_right_layout, this);
        mTVtitle = findViewById(R.id.info_item_et_layout_TV_title);
        mTVimportent = findViewById(R.id.info_item_et_layout_TV_import);
        mET = findViewById(R.id.info_item_et_layout_ET_content);
        mLable = findViewById(R.id.record_item_layout_TV_label);
        initAttrs(context, attrs);
        if (importent) {
            mTVimportent.setVisibility(VISIBLE);
        } else {
            mTVimportent.setVisibility(GONE);
        }
        mTVtitle.setText(title);
        mET.setHint(hint);
        mET.setSingleLine(true);
        mET.setEllipsize(TextUtils.TruncateAt.END);
        switch (input_type) {
            case PHONE:
                String digitsPhone = getResources().getString(R.string.phoneCode);
                mET.setKeyListener(DigitsKeyListener.getInstance(digitsPhone));
                mET.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                break;
            case USERNAME:
                //            String digitsPhone = getResources().getString(R.string.filter_vcode);
                //            mET.setKeyListener(DigitsKeyListener.getUrl(digitsPhone));
                //            mET.setTextAppearance(context, R.style.EditText_UserName_style);
                break;
            case NUMBER:
                String digitsNumber = getResources().getString(R.string.phoneCode);
                mET.setKeyListener(DigitsKeyListener.getInstance(digitsNumber));
                break;
            case PASSWORD:
                mET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                String digitsPassword = getResources().getString(R.string.password_vcode);
                mET.setKeyListener(DigitsKeyListener.getInstance(digitsPassword));
                break;
            case ID_CARD:
                String digitsIdCard = getResources().getString(R.string.idCode);
                mET.setKeyListener(DigitsKeyListener.getInstance(digitsIdCard));
                break;
        }
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.InfoItemETLayout);
        title = array.getString(R.styleable.InfoItemETLayout_title);
        hint = array.getString(R.styleable.InfoItemETLayout_hint_Str);
        input_type = array.getInt(R.styleable.InfoItemETLayout_input_type, 0);
        importent = array.getBoolean(R.styleable.InfoItemETLayout_importent, false);
        array.recycle();
    }

    public String getContent() {
        return mET.getText().toString();
    }

    public void setContent(String content, String label) {
        content = content == null ? "" : content;
        if (content.equals("")) {
            mET.setHint(hint);
        } else {
            mET.setText(content);
            mLable.setText(label);
            //光标移动至最后
            Selection.setSelection(mET.getText(), mET.getText().length());
        }
    }

    public void setHint(String mHint) {
        if (!TextUtils.isEmpty(mHint)) {
            mET.setHint(mHint);
        }
    }

    /**
     * 输入框是否可编辑
     *
     * @param canEdit 是否可编辑
     * @author Went_Gone
     * @time 2017/3/29 14:34
     */
    public void canEdit(boolean canEdit) {
        mET.setFocusable(canEdit);
        mET.setEnabled(canEdit);
    }

    /**
     * 输入框显示边框设置
     */
    public void setEdit() {
        mET.setBackground(getResources().getDrawable(R.drawable.bg_edittext));
    }

    public EditText getET() {
        return mET;
    }

    public boolean isImportent() {
        return importent;
    }

    public void setImportent(boolean importent) {
        this.importent = importent;
    }

    public void setTextSize(float size) {
        mTVtitle.setTextSize(DensityUtil.dip2px(mContext, size + 0.3f));
    }

    public String getTitle() {
        return mTVtitle.getText().toString();
    }

    public void setTextColoer() {
        mET.setTextColor(mContext.getResources().getColor(R.color.item_title_TextColor_light));
    }


    public void setETChangeListen() {
        if (input_type == NUMBER) {
            mET.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    mLable.setVisibility(View.VISIBLE);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String words = s.toString();
                    //首先内容进行非空判断，空内容（""和null）不处理
                    if (!TextUtils.isEmpty(words)) {
                        if (Double.parseDouble(words) > 99) {

                            if (words.length() > 2) {
                                //若输入不合规，且长度超过2位，继续输入只显示之前存储的outStr
                                words = words.substring(0, words.length() - 1);
                                mET.setText(words);
                                //重置输入框内容后默认光标位置会回到索引0的地方，要改变光标位置
                                mET.setSelection(words.length());
                            }
                            ToastUtil.getLongToastByString(mContext, getResources().getString(R.string.error_prompt));
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(mET.getText().toString())) {
                        mLable.setVisibility(View.GONE);
                        mET.setHint("请输入");
                    } else {
                        //这里的处理是不让输入0开头的值
                        String words = s.toString();
                        //首先内容进行非空判断，空内容（""和null）不处理
                        if (!TextUtils.isEmpty(words)) {
                            if (Double.parseDouble(s.toString()) < 0) {
                                mET.setText("");
                                ToastUtil.getLongToastByString(mContext, getResources().getString(R.string.error_prompt));
                            }
                        }
                    }
                }
            });
        }
    }


}

