package cn.demo.quickdemo.ui.main;

import butterknife.BindView;
import cn.demo.quickdemo.R;
import cn.demo.quickdemo.customview.SpecialTab;
import cn.plugin.core.base.BasePresenter;
import cn.plugin.core.base.MVPBaseActivity;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;

/**主页
 * Created by yy on 2019/11/7.
 **/
public class MainActivity extends MVPBaseActivity {


    public static final String INTENT_FROM_TYPE = "inent_from_type";
    private static final int DOWNLOAD_REQUEST_CODE = 0x67;
    public static final int LOGIN_ACTIVITY = 0;
    public static final int SPLASH_ACTIVITY = 1;
    private static final int PAGE_NUM = 5;
    private NavigationController navigationController;
    @BindView(R.id.tab)
    PageNavigationView tab;
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
        navigationController = tab.custom()
                .addItem(newItem(R.mipmap.navigation_patient_icon_unselected, R.mipmap
                        .navigation_patient_icon_selected, "患者"))
                .addItem(newItem(R.mipmap.navigation_followup_icon_unselected, R.mipmap
                        .navigation_followup_icon_selected, "随访"))
                .addItem(newItem(R.mipmap.navigation_msg_icon_unselected, R.mipmap
                        .navigation_msg_icon_selected, "消息"))
                .addItem(newItem(R.mipmap.navigation_mine_icon_unselected, R.mipmap
                .navigation_mine_icon_selected, "我的"))
                .build();
    }

    @Override
    public void setListeners() {

    }
    /**
     * 显示正常tab
     */
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        SpecialTab mainTab = new SpecialTab(this);
        mainTab.initialize(drawable, checkedDrawable, text);
        mainTab.setTextDefaultColor(getResources().getColor(R.color.item_title_TextColor));
        mainTab.setTextCheckedColor(getResources().getColor(R.color.title_bar));
        return mainTab;
    }

//    @OnClick(R.id.btn)
//    public void onViewClicked() {
//        Intent intent = new Intent(this, WebViewActivity.class);
//        intent.putExtra(WebViewActivity.KEY_TITLE, "百度");
//        intent.putExtra(WebViewActivity.KEY_URL, "https://www.baidu.com");
//        startActivity(intent);
//    }
}
