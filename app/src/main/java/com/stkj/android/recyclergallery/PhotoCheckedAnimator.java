package com.stkj.android.recyclergallery;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import jp.wasabeef.recyclerview.animators.BaseItemAnimatorEx;

/**
 * Created by jarrah on 2015/8/18.
 */
public class PhotoCheckedAnimator extends BaseItemAnimatorEx{

    public PhotoCheckedAnimator() {
        setChangeDuration(300);
    }

    @Override
    protected void animateRemoveImpl(RecyclerView.ViewHolder holder) {

    }

    @Override
    protected void preAnimateAddImpl(RecyclerView.ViewHolder holder) {
        ViewCompat.setAlpha(holder.itemView, 0);
    }

    @Override
    protected void animateAddImpl(final RecyclerView.ViewHolder holder) {
        ViewCompat.animate(holder.itemView)
                .alpha(1)
                .setDuration(getAddDuration())
                .setListener(new DefaultAddVpaListener(holder)).start();
    }

    @Override
    protected void preAnimateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder) {

        if(newHolder.getItemViewType() == LocalAdapter.ViewType.CHECKED) {
            ViewCompat.setAlpha(newHolder.itemView, 0f);
            ViewCompat.setAlpha(oldHolder.itemView, 0f);
            ViewCompat.setRotationY(newHolder.itemView, -80f);
        }else {
            ViewCompat.setAlpha(newHolder.itemView, 0f);
            ViewCompat.setAlpha(oldHolder.itemView, 1f);
            ViewCompat.setRotationY(oldHolder.itemView, 0f);
        }
    }

    @Override
    protected void animateChangeImpl(ChangeInfo changeInfo) {
        AnimatorSet set = new AnimatorSet();

        final RecyclerView.ViewHolder newHolder = changeInfo.newHolder;
        final RecyclerView.ViewHolder oldHolder = changeInfo.oldHolder;

        if(changeInfo.newHolder.getItemViewType() == LocalAdapter.ViewType.CHECKED) {
            ObjectAnimator alpha = ObjectAnimator.ofFloat(newHolder.itemView, "alpha", 1f);
            ObjectAnimator rotate = ObjectAnimator.ofFloat(newHolder.itemView, "rotationY", 0f);
            set.playTogether(alpha, rotate);
        }else {
            ObjectAnimator alpha = ObjectAnimator.ofFloat(oldHolder.itemView, "alpha", 0f);
            ObjectAnimator rotate = ObjectAnimator.ofFloat(oldHolder.itemView, "rotationY", -160f);
            set.playTogether(alpha, rotate);
            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    ViewCompat.setAlpha(newHolder.itemView, 0.4f);
                    ViewCompat.animate(newHolder.itemView).setDuration(getChangeDuration()).alpha(1f);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            set.setDuration(getChangeDuration());
        }
        set.start();
    }
}
