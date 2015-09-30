package com.stkj.android.locker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.locker.R;
import com.stkj.android.recyclergallery.ImageHelper;

import java.util.HashMap;
import java.util.Map;

public class ActivityMain extends AppCompatActivity {

    public static final String ARGUMENTS = "f_args";

    public static Map<String, Class<? extends Fragment>> FRAGMENT_MAPPING = new HashMap<String, Class<? extends Fragment>>();

    public static final String ACTION_LOCKER_ABOUT = "ACTION.ABOUT";
    public static final String ACTION_LOCKER_ONLINE = "ACTION.ONLINE.WALL.PAPER";
    public static final String ACTION_LOCKER_LOCAL = "ACTION.LOCAL.WALL.PAPER";
    public static final String ACTION_LOCKER_SETTING = "ACTION.LOCKER.SETTING";
    public static final String ACTION_LOCKER_PASSWORD = "ACTION.LOCKER.PASSWORD";
    public static final String ACTION_LOCKER_MSG_NOTIFY = "ACTION.LOCKER.MSG.NOTIFY";

    static {
        FRAGMENT_MAPPING.put(ACTION_LOCKER_LOCAL, FragmentLocalWall.class);
        FRAGMENT_MAPPING.put(ACTION_LOCKER_ONLINE, FragmentOnlineWall.class);
        FRAGMENT_MAPPING.put(ACTION_LOCKER_SETTING, FragmentLockerSetting.class);
        FRAGMENT_MAPPING.put(ACTION_LOCKER_MSG_NOTIFY, FragmentMsgNotify.class);
        FRAGMENT_MAPPING.put(ACTION_LOCKER_ABOUT, FragmentAbout.class);
        FRAGMENT_MAPPING.put(ACTION_LOCKER_PASSWORD, FragmentLockerPassword.class);
    }

    private Fragment mDisplayFragment;
    private android.support.design.widget.NavigationView navigation;
    private android.support.v4.widget.DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupActionBar();
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        this.navigation = (NavigationView) findViewById(R.id.navigation);
        this.navigation.setNavigationItemSelectedListener(new OnNavItemClickImpl());
        this.drawerLayout.setDrawerListener(new OptimizeWatcher());
        switchFragment(getAction(), false);
    }

    public void switchFragment(String action, boolean addToBackStack) {

        if(action == null) {
            return;
        }

        Class<? extends Fragment> clz = FRAGMENT_MAPPING.get(action);
        if(clz == null) {
            return;
        }

        String fragmentName =  clz.getName();
        mDisplayFragment = Fragment.instantiate(this, fragmentName, getArguments());

        if(mDisplayFragment == null) {
            return;
        }

        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.add(R.id.fragment_container, mDisplayFragment, fragmentName);
        if(addToBackStack)
            trans.addToBackStack(fragmentName);
        trans.commit();

        updateTitle(mDisplayFragment);

        Log.from(this, "switch to " + mDisplayFragment.getClass().getName());
    }



    @Override
    public void onBackPressed(){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

        if(this.drawerLayout.isDrawerOpen(this.navigation)) {
            this.drawerLayout.closeDrawers();
            return;
        }

        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStackImmediate();
            mDisplayFragment = getActiveFragment();
      }else {
            finish();
        }
        Log.from(this, "display fragment is " + mDisplayFragment);
        updateTitle(mDisplayFragment);
    }

    public Fragment getActiveFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {//如果back 没有 fragment 把 在线壁纸设为第一个
            return getSupportFragmentManager().findFragmentByTag(FragmentOnlineWall.class.getName());
        }
        String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        Log.from(this, "active fragment tag " + tag);
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    private void setupActionBar() {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private String getAction() {
        String action = getIntent().getAction();
        if(action == null || action.equals(Intent.ACTION_MAIN)) { //默认显示 在线壁纸
            action = ACTION_LOCKER_ONLINE;
        }
        return action;
    }

    private Bundle getArguments() {
        Bundle arguments = getIntent().getBundleExtra(ARGUMENTS);
        return arguments;
    }


    public static void start(Activity activity, String action) {
        Log.from(activity, "start : " + action);
        Intent intent = new Intent(action);
        intent.setClass(activity, ActivityMain.class);
        activity.startActivity(intent);
    }

    private static class OptimizeWatcher implements DrawerLayout.DrawerListener {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            ImageHelper.getImageLoader().pause();
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            ImageHelper.getImageLoader().pause();
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            ImageHelper.getImageLoader().resume();
        }

        @Override
        public void onDrawerStateChanged(int newState) {
        }
    }

    private class OnNavItemClickImpl implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            String action = null;
            menuItem.setChecked(true);
            drawerLayout.closeDrawers();
            switch (menuItem.getItemId()) {
                case R.id.local_wall:
                    action = ACTION_LOCKER_LOCAL;
                    break;

                case R.id.locker_setting:
                    action = ACTION_LOCKER_SETTING;
                    break;

                case R.id.online_wall:
                    action = ACTION_LOCKER_ONLINE;
                    break;

                case R.id.about:
                    action = ACTION_LOCKER_ABOUT;
                    break;

                case R.id.locker_pwd:
                    action = ACTION_LOCKER_PASSWORD;
                    break;

                case R.id.msg_notify:
                    action = ACTION_LOCKER_MSG_NOTIFY;
                    break;
                default:
                    break;
            }
            if(action != null) {
                switchFragment(action, true);
            }
            return true;
        }
    }

    private void updateTitle(Fragment displayFragment) {
        Log.from(this, "update title " + displayFragment);
        if(displayFragment instanceof IDrawer) {
            int titleRes = ((IDrawer)displayFragment).getDrawerTitle();
            setTitle(titleRes);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ActivityMain.class));
    }
}
