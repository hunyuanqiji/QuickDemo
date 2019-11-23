package cn.plugin.core.widgets;

import android.net.http.SslError;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import cn.plugin.core.R;
import cn.plugin.core.R2;
import cn.plugin.core.base.BasePresenter;
import cn.plugin.core.base.MVPBaseActivity;
import cn.plugin.core.utils.LogUtil;

/**
 * 通用webview
 * Create by Went_Gone on 2017/5/3.
 */
public class WebViewActivity extends MVPBaseActivity {
    private static final String TAG = WebViewActivity.class.getSimpleName();

    public static final String KEY_TITLE = "title";
    public static final String KEY_URL = "url";

    @BindView(R2.id.titlebar)
    TitleBarLayout titlebar;
    @BindView(R2.id.webView)
    WebView webView;
    @BindView(R2.id.empty_layout)
    EmptyLayout emptyLayout;

    private String mTitle;
    private String mUrl;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initBeforData() {
        if (getIntent() != null) {
            mTitle = getIntent().getStringExtra(KEY_TITLE);
            mUrl = getIntent().getStringExtra(KEY_URL);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initViews() {
        titlebar.setTitle(mTitle);
    }

    @Override
    public void setListeners() {
        showLoading();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBlockNetworkImage(false);
        webView.getSettings().setBlockNetworkLoads(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setVerticalScrollBarEnabled(false); //垂直不显示

        if (!TextUtils.isEmpty(mUrl)) {
            webView.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            webView.loadUrl(mUrl);
            LogUtil.e(TAG, "url:" + mUrl);
        } else {
            webView.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                hideLoading();
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                hideLoading();
                handler.proceed();
            }
        });
    }
}
