package cn.demo.quickdemo.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.demo.quickdemo.R;
import cn.plugin.core.base.BasePresenter;
import cn.plugin.core.base.MVPBaseActivity;
import cn.plugin.core.widgets.WebViewActivity;

/**
 * Created by yy on 2019/11/7.
 **/
public class MainActivity extends MVPBaseActivity {

    @BindView(R.id.btn)
    Button btn;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initBeforData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void setListeners() {

    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(WebViewActivity.KEY_TITLE, "百度");
        intent.putExtra(WebViewActivity.KEY_URL, "https://www.baidu.com");
        startActivity(intent);
    }
}
