package eu.janmuller.android.simplecropimage;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


/**
 * Created by mindlabs on 11/10/15.
 */
public class CustomProgressDialog extends ProgressDialog {
    Animation animRotate;
    private AnimationDrawable animation;

    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public static ProgressDialog nowRunningDialog(Context context) {
        CustomProgressDialog dialog = new CustomProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(true);
        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_custom_progress);
        ImageView la = (ImageView) findViewById(R.id.imageProgress);
        animRotate = AnimationUtils.loadAnimation(getContext(),
                R.anim.rotate_anim);
        la.startAnimation(animRotate);
//        la.setImageResource(R.drawable.custom_progress_animation);
//        this.animation = (AnimationDrawable) la.getDrawable();
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCancelable(false);
    }

    /*@Override
    public void show() {
        if (animation != null)
            this.animation.start();

        super.show();
    }

    @Override
    public void dismiss() {
        if (animation != null)
            this.animation.stop();

        super.dismiss();
    }*/

    /*public void show() {
        super.show();
        if (animation != null)
        this.animation.start();
    }

    public void dismiss() {
        super.dismiss();

        if (animation != null)
        this.animation.stop();
    }*/
}
