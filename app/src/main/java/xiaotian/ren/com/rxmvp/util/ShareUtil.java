package xiaotian.ren.com.rxmvp.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import xiaotian.ren.com.rxmvp.R;

/**
 * Created by admin on 2016/5/6.
 */
public class ShareUtil {

    public static void share(Context context,int resId){
        shareTxt(context,context.getResources().getString(resId));
    }
    public static void shareTxt(Context context,String extraText){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.share));
        intent.putExtra(Intent.EXTRA_TEXT, extraText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(
                Intent.createChooser(intent, context.getString(R.string.share)));
    }

    public static void shareImage(Context context, Uri uri, String title) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(shareIntent, title));
    }
}

