package cn.plugin.core.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**     
* 类描述：管理所有activity
* 创建人：宁家琦   
* 创建时间：2015-5-5 下午12:55:15     
* @version    
*/
public class AppManager {
	private static AppManager sInstance = null;
	private static Stack<Activity> sActivityStack;

	private AppManager() {
	}

	/**
	 * 单一实例
	 */
	public static AppManager getAppManager() {
		if (sInstance == null) {
			synchronized (AppManager.class) {
				if (sInstance == null) {
					sInstance = new AppManager();
				}
			}
		}
		return sInstance;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity) {
		if (sActivityStack == null) {
			sActivityStack = new Stack<>();
		}
		sActivityStack.add(activity);
	}

	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity() {
		if (sActivityStack != null) {
			Activity activity = sActivityStack.lastElement();
			return activity;
		} else {
			return null;
		}
	}

	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity() {
		Activity activity = sActivityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			sActivityStack.remove(activity);
			activity.finish();
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : sActivityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
				break;
			}
		}
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = sActivityStack.size(); i < size; i++) {
			if (null != sActivityStack.get(i)) {
				sActivityStack.get(i).finish();
			}
		}
		sActivityStack.clear();
	}

	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			finishAllActivity();
//			ActivityManager activityMgr = (ActivityManager) context
//					.getSystemService(Context.ACTIVITY_SERVICE);
//			activityMgr.restartPackage(context.getPackageName());
//			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 当前activity是否存在
	public boolean isExistActivity(Class<?> cls) {
		for (Activity activity : sActivityStack) {
			if (activity.getClass().equals(cls)) {
				return true;
			}
		}
		return false;
	}

	// 移除指定Activity
	public void removeActivity(Activity activity) {
		if (sActivityStack.contains(activity)) {
			sActivityStack.remove(activity);
		}
	}
}
