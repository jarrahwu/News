package com.stkj.android.locker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.locker.R;

/**
 * Created by jarrah on 2015/9/22.
 */
public class FragmentMsgNotify extends BaseFragment implements IDrawer {

    public FragmentMsgNotify() {
    }

    @Override
    public View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_msg_notify, container, false);
    }

    @Override
    public int getDrawerTitle() {
        return R.string.msg_notify;
    }
}