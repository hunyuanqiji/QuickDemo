package cn.plugin.core.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.plugin.core.R;

/**
 * Created by Administrator on 2018/6/22.
 */

public class EmptyLayout extends LinearLayout {
    private String emptyString,errorString;
    private int imageRes;
    private ImageView mIV;
    private TextView mTV;
    private TextView mTVError;
    private View mViewTop,mViewBottom;
    private float topWeight = 1;
    private float bottomWeight = 1.4f;
    private String weightString = "";
    private boolean isError;
    private boolean haveDate;

    public EmptyLayout(Context context) {
        this(context,null);
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.empty_layout,this);
        mTV = findViewById(R.id.empty_layout_TV);
        mTVError = findViewById(R.id.empty_layout_TV_Error);
        mIV = findViewById(R.id.empty_layout_IV);
        mViewTop = findViewById(R.id.empty_layout_View_Top);
        mViewBottom = findViewById(R.id.empty_layout_View_Bottom);

        initAttr(context,attrs);
        if (TextUtils.isEmpty(weightString)){
            topWeight = 1;
            bottomWeight = 1.4f;
        }else {
            if (weightString.contains(":")){
                String[] split = weightString.split(":");
                if (split.length>1){
                    try {
                        topWeight = Float.parseFloat(split[0]);
                    }catch (Exception e){
                        topWeight = 1;
                    }
                    try {
                        bottomWeight = Float.parseFloat((split[1]));
                    }catch (Exception e){
                        bottomWeight = 1.4f;
                    }
                }else if (weightString.length()>0){
                    if (weightString.startsWith(":")){
                        try {
                            bottomWeight = Float.parseFloat((split[1]));
                        }catch (Exception e){
                            bottomWeight = 1.4f;
                        }
                    }else if (weightString.endsWith(":")){
                        try {
                            topWeight = Float.parseFloat(split[0]);
                        }catch (Exception e){
                            topWeight = 1;
                        }
                    }
                }
            }else {
                try {
                    topWeight = Float.parseFloat(weightString);
                }catch (Exception e){
                    topWeight = 1;
                }
                try {
                    bottomWeight = Float.parseFloat((weightString));
                }catch (Exception e){
                    bottomWeight = 1.4f;
                }
            }
        }

        LayoutParams lpTop = (LayoutParams) mViewTop.getLayoutParams();
        lpTop.weight = topWeight;
        LayoutParams lpBottom = (LayoutParams) mViewBottom.getLayoutParams();
        lpBottom.weight = bottomWeight;

        mIV.setImageResource(imageRes);
        mTV.setText(emptyString);
        mTVError.setText(errorString);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.EmptyLayout);
        emptyString = array.getString(R.styleable.EmptyLayout_emptyString);
        errorString = array.getString(R.styleable.EmptyLayout_errorString);
        imageRes = array.getResourceId(R.styleable.EmptyLayout_icon,R.drawable.login_logo);
        weightString = array.getString(R.styleable.EmptyLayout_weightString);
        array.recycle();
    }

    private void setEmpty(){
        isError = false;
        mTVError.setVisibility(GONE);
        mTV.setVisibility(VISIBLE);
        mTV.setText(emptyString);
    }

    private void setError(){
        isError = true;
        mTV.setVisibility(GONE);
        mTVError.setVisibility(VISIBLE);
        mTVError.setText(errorString);
    }

    public void setType(final boolean isError){
        if (isError){
            setError();
        }else {
            setEmpty();
        }
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isError){
                    if (emptyInterface != null){
                        emptyInterface.onErrorClick();
                    }
                }
            }
        });
    }

    public void setEmpty(boolean isEmpty){
        this.setVisibility(isEmpty?VISIBLE:GONE);
        setEmpty();
    }

    public void setError(boolean isError){
        this.setVisibility(isError?VISIBLE:GONE);
        setError();
    }

    public boolean isError() {
        return isError;
    }

    private EmptyInterface emptyInterface;

    public void setEmptyInterface(EmptyInterface emptyInterface) {
        this.emptyInterface = emptyInterface;
    }

    public interface EmptyInterface{
        void onErrorClick();
    }

    public void setEmptyString(String emptyString) {
        this.emptyString = emptyString;
        mTV.setText(emptyString);
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
        mIV.setImageResource(imageRes);
    }

    public String getEmptyString() {
        return emptyString;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setTopWeight(float topWeight) {
        this.topWeight = topWeight;
        LayoutParams lpTop = (LayoutParams) mViewTop.getLayoutParams();
        lpTop.weight = topWeight;
    }

    public void setBottomWeight(float bottomWeight) {
        this.bottomWeight = bottomWeight;
        LayoutParams lpBottom = (LayoutParams) mViewBottom.getLayoutParams();
        lpBottom.weight = bottomWeight;
    }
}
