package xiaotian.ren.com.rxmvp.util;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import xiaotian.ren.com.rxmvp.R;
import xiaotian.ren.com.rxmvp.ui.activity.MainActivity;
import xiaotian.ren.com.rxmvp.ui.view.RotateAnimation;

/**
 * Created by admin on 2016/5/6.
 */
public class ToolBarHandler {

    private Toolbar mToolBar;
    private MainActivity activity;
    public ToolBarHandler(MainActivity activity) {
        if(!(activity instanceof MainActivity)){
            try {
                throw new ParamException("参数传递错误");
            } catch (ParamException e) {
                e.printStackTrace();
            }
            return;
        }
        this.activity = activity;
        mToolBar =  activity.getToolBar();
    }

    public void MenuClickListener(MenuItem menuItem){
       if(activity == null){
           return;
       }
        switch (menuItem.getItemId()){
            case R.id.action_item_more:
                showToolbarAnimation();
                break;
            case R.id.action_items_like:
                Snackbar.make(activity.getFloatBtn(),"你喜欢谁？？？",Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.action_items_share:
                ShareUtil.shareTxt(activity,"任晓天的笑话大全");
                break;
        }
    }

    /**
     * toolbar切换动画
     */
    private void showToolbarAnimation() {
        float centerX;
        float centerY;
        float start;
        float end;

        View menuView = activity.getToolBar().getChildAt(2);
        centerX = menuView.getX() - menuView.getWidth()/2;
        centerY = menuView.getY() + menuView.getHeight() / 2;
        start = -90;
        end = 0;
        applyRotation(menuView, start, end, centerX, centerY, true);
    }

    private void applyRotation(View menuView, float start, float end,
                               float centerX, float centerY, boolean isRotateX) {
        RotateAnimation rotation =
                new RotateAnimation(start, end, centerX, centerY, 10, false, isRotateX);
        rotation.setDuration(300);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        menuView.startAnimation(rotation);
    }

    public void showToolBar(boolean isAnim) {
        mToolBar.getMenu().clear();
        mToolBar.inflateMenu(R.menu.menu_main);
        if(isAnim){
            showToolbarAnimation();
        }
    }

    private class ParamException extends Exception{
        public ParamException(String s) {
            System.out.print(s);
        }
    }
}
