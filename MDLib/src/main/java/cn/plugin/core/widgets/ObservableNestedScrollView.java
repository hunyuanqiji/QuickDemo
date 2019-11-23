package cn.plugin.core.widgets;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * Created by yy on 2019/1/26.
 **/
public class ObservableNestedScrollView extends NestedScrollView {
    private ScrollViewListener scrollViewListener = null;

    public ObservableNestedScrollView(Context context) {
        super(context);
    }

    public ObservableNestedScrollView(Context context, AttributeSet attrs,
                                      int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public interface ScrollViewListener{
        void onScrollChanged(ObservableNestedScrollView view, int x, int y, int oldx, int oldy);
    }
}
