package cn.plugin.core.widgets;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;


/**
 * Created by 冯超 on 2017/2/20.
 */
public class CustomBottomDialog {
    public static Dialog getDialog(Context context,int layout){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.getWindow().getAttributes().gravity = Gravity.BOTTOM;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth();
        dialog.getWindow().setAttributes(lp);
        return  dialog;
    }
}
