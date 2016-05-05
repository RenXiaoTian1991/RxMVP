package xiaotian.ren.com.rxmvp.util;

import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by admin on 2016/5/5.
 */
public class AnimUtil {

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
}
