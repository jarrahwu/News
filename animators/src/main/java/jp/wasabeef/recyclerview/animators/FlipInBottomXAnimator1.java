package jp.wasabeef.recyclerview.animators;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class FlipInBottomXAnimator1 extends BaseItemAnimatorEx {

    @Override
    protected void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
    }

    @Override
    protected void preAnimateAddImpl(RecyclerView.ViewHolder holder) {
    }

    @Override
    protected void animateAddImpl(final RecyclerView.ViewHolder holder) {
    }

    @Override
    protected void preAnimateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder) {
        ViewCompat.setAlpha(oldHolder.itemView, 0f);
        ViewCompat.setAlpha(newHolder.itemView, 1f);
        ViewCompat.setRotationX(newHolder.itemView, -720f);
        ViewCompat.setScaleX(newHolder.itemView, 0.6f);
        ViewCompat.setScaleY(newHolder.itemView, 0.6f);
    }

    @Override
    protected void animateChangeImpl(BaseItemAnimatorEx.ChangeInfo changeInfo) {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator rotationX = ObjectAnimator.ofFloat(changeInfo.newHolder.itemView, "rotationX", 0f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(changeInfo.newHolder.itemView, "scaleX", 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(changeInfo.newHolder.itemView, "scaleY", 1.0f);
        set.playTogether(rotationX, scaleX, scaleY);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.start();
    }
}
