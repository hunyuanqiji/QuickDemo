package cn.plugin.core.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cn.plugin.core.R;
import cn.plugin.core.widgets.EmptyLayout;


/**
 * Created by Yang_Yang on 2018/10/26 0026
 */
public class EmptyViewUtil {

    /**
     * 空白布局
     * @param recyclerView
     * @param showMessage 展示消息
     * @param context
     * @return
     */
    public static View getNotDataView(RecyclerView recyclerView, String showMessage, Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.empty_view, (ViewGroup)
                recyclerView.getParent(), false);
        if (!TextUtils.isEmpty(showMessage)) {
            EmptyLayout emptyLayout = view.findViewById(R.id.EmptyLayout);
            emptyLayout.setEmptyString(showMessage);
        }
        return view;
    }

    /**
     * 空白布局
     * @param recyclerView
     * @param context
     * @return
     */
    public static View getNotDataView(RecyclerView recyclerView, Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.empty_view, (ViewGroup)
                recyclerView.getParent(), false);
        return view;
    }


    /**
     * 黑名单管理空白布局
     * @param recyclerView
     * @param context
     * @return
     */
    public static View getNotDataView(RecyclerView recyclerView,int drawableID,String showMessage,String errirString, Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.empty_view, (ViewGroup)
                recyclerView.getParent(), false);
        EmptyLayout emptyLayout = view.findViewById(R.id.EmptyLayout);
        if (showMessage!=null) {
            emptyLayout.setEmptyString(showMessage);
            emptyLayout.setErrorString(errirString);
        }
        emptyLayout.setImageRes(drawableID);
        return view;
    }

    /**
     * 空白布局
     * @param recyclerView
     * @param context
     * @return
     */
    public static View getNotDataViewSearchHospital(RecyclerView recyclerView,
                                                    Context context,
                                                    View.OnClickListener listener) {
        View view = LayoutInflater.from(context).inflate
                (R.layout.layout_search_hospital_empty, (ViewGroup)
                recyclerView.getParent(), false);
        Button btnCommit = view.findViewById(R.id.btn_commit);
        btnCommit.setOnClickListener(listener);
        return view;
    }

}
