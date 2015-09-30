package com.stkj.android.locker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.locker.R;
import com.stkj.android.view.TextSwitch;

/**
 * Created by jarrah on 2015/9/18.
 */
public class FragmentLockerSetting extends BaseFragment implements IDrawer{


    private com.stkj.android.view.TextSwitch lockerEnable;
    private com.stkj.android.view.TextSwitch msgNotify;
    private com.stkj.android.view.TextSwitch hideMsgContent;
    private com.stkj.android.view.TextSwitch lightDevice;

    public FragmentLockerSetting() {}


    @Override
    public View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_locker_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.lightDevice = (TextSwitch) view.findViewById(R.id.lightDevice);
        this.hideMsgContent = (TextSwitch) view.findViewById(R.id.hideMsgContent);
        this.msgNotify = (TextSwitch) view.findViewById(R.id.msgNotify);
        this.lockerEnable = (TextSwitch) view.findViewById(R.id.lockerEnable);

        this.lockerEnable.switchCompat.setOnCheckedChangeListener(new LockerEnableSwitchImpl(this.lockerEnable));
        this.hideMsgContent.switchCompat.setOnCheckedChangeListener(new SwitchImpl(this.hideMsgContent));
        this.msgNotify.switchCompat.setOnCheckedChangeListener(new SwitchImpl(this.msgNotify));
        this.lightDevice.switchCompat.setOnCheckedChangeListener(new SwitchImpl(this.lightDevice));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setSwitchValue(this.lockerEnable);
        setSwitchValue(this.lightDevice);
        setSwitchValue(this.hideMsgContent);
        setSwitchValue(this.msgNotify);
    }

    private void setSwitchValue(TextSwitch textSwitch) {
        textSwitch.switchCompat.setChecked(TogglePreference.getsInstance().isChecked(textSwitch.getId()));
    }

    @Override
    public int getDrawerTitle() {
        return R.string.locker_setting;
    }

    private class SwitchImpl implements CompoundButton.OnCheckedChangeListener {

        private final TextSwitch textSwitch;

        public SwitchImpl(TextSwitch textSwitch) {
            this.textSwitch = textSwitch;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            TogglePreference.getsInstance().toggle(this.textSwitch.getId());
        }
    }

    private class LockerEnableSwitchImpl extends SwitchImpl {

        public LockerEnableSwitchImpl(TextSwitch textSwitch) {
            super(textSwitch);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            super.onCheckedChanged(buttonView, isChecked);
            if(isChecked) {
                LockerService.enableLocker(getActivity());
            }else {
                LockerService.disableLocker(getActivity());
            }
        }
    }
}
