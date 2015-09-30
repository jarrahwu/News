package com.stkj.android.locker;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locker.R;

/**
 * Created by jarrah on 2015/9/22.
 */
public class FragmentLockerPassword extends BaseFragment implements IDrawer {

    private static final int[] PASSWORD_BOX_IDS = new int[]{R.id.pb0, R.id.pb1, R.id.pb2, R.id.pb3};
    private static final int[] KEY_NUM_IDS = new int[]{R.id._0, R.id._1, R.id._2, R.id._3, R.id._4, R.id._5, R.id._6, R.id._7, R.id._8, R.id._9};

    private String mInput = "";
    private String mTemp = "";
    private View mClear;
    private View mBackspace;
    private TextView mInputHint;

    public FragmentLockerPassword() {
    }

    @Override
    public View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_locker_password, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for (int i = 0; i < KEY_NUM_IDS.length; i++) {//密码点击事件
            getKey(i).setOnClickListener(new OnKeyNumClickImpl());
        }

        mClear = view.findViewById(R.id._C);
        mBackspace = view.findViewById(R.id._Del);
        mClear.setOnClickListener(new OnClearClickImpl());
        mBackspace.setOnClickListener(new OnBackspaceClickImpl());
        mInputHint = (TextView) view.findViewById(R.id.passwd_input_hint);
    }

    @Override
    public int getDrawerTitle() {
        return R.string.locker_pwd;
    }

    private TextView getPasswordBox(int i) {
        return (TextView) mView.findViewById(PASSWORD_BOX_IDS[i]);
    }

    private TextView getKey(int i) {
        return (TextView) mView.findViewById(KEY_NUM_IDS[i]);
    }

    private class OnClearClickImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            clearInputBox();
        }
    }

    private void clearInputBox() {
        mInput = "";
        for (int i = 0; i < PASSWORD_BOX_IDS.length; i++) {
            getPasswordBox(i).setText("");
        }
    }

    private class OnBackspaceClickImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (!TextUtils.isEmpty(mInput)) {
                String sub = mInput.substring(0, mInput.length() - 1);
                getPasswordBox(mInput.length() - 1).setText("");
                mInput = sub;
            }
        }
    }

    private class OnKeyNumClickImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int currentIndex = mInput.length();
            if (currentIndex < PASSWORD_BOX_IDS.length) {
                //同步显示
                TextView tv = (TextView) v;
                CharSequence num = tv.getText();
                getPasswordBox(currentIndex).setText(num);
                mInput += num;

                //处理输入完毕逻辑
                if(mInput.length() == PASSWORD_BOX_IDS.length) {//输入完毕
                    if(TextUtils.isEmpty(mTemp)) { //显示再次输入
                        mTemp = mInput;
                        mInputHint.setText(R.string.input_again);
                        clearInputBox();
                    }else {
                        if (mTemp.equals(mInput)) {//设置完毕
                            Toast.makeText(getActivity(), R.string.password_input_done, Toast.LENGTH_SHORT).show();//前后一致,成功
                            getActivity().onBackPressed();
                        }else {
                            Toast.makeText(getActivity(), R.string.pasaword_incorret_input_again, Toast.LENGTH_SHORT).show();//前后不一致, 失败重新输入
                            mTemp = "";
                            mInputHint.setText(R.string.input_locker_pwd);
                            clearInputBox();
                        }
                    }

                }
            }
            Log.from(this, "INPUT " + mInput);
        }
    }
}
