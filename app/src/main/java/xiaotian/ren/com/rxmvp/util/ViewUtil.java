package xiaotian.ren.com.rxmvp.util;

import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by admin on 2016/5/6.
 */
public class ViewUtil {
    private static int screenWidth = 0;
    private static int screenHeight = 0;
    private static int appBarHeight = 0;

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            screenHeight = dm.heightPixels;
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            screenWidth = dm.widthPixels;
        }
        return screenWidth;
    }

    public static void setAppBarHeight(int height) {
        appBarHeight = height;
    }

    public static int getAppBarHeight() {
        return appBarHeight;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        if (context != null) {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    public static void showMsg(View view, CharSequence msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    public static void showMsgLong(View view, CharSequence msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    public static void showMsg(Context context, View view, int strId) {
        Snackbar.make(view, context.getResources().getText(strId), Snackbar.LENGTH_SHORT).show();
    }

    public static void listItemUpAnim(View view, int position,
                                      AnimatorListenerAdapter animatorListenerAdapter) {
        view.setTranslationY(150);
        view.setAlpha(0.f);
        ViewPropertyAnimator animate = view.animate();
        animate.translationY(0).alpha(1.f)
                .setStartDelay(20 * (position))
                .setInterpolator(new DecelerateInterpolator(2.f))
                .setDuration(400);

        if (animatorListenerAdapter != null) {
            animate.setListener(animatorListenerAdapter);
        }

        animate.withLayer().start();
    }

    public static View.OnClickListener getClick(final Context context,final Dialog dialog){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){

                }
            }
        };
    }

}
