package com.stkj.android.locker;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.locker.R;

/**
 * Created by jarrah on 2015/9/18.
 */
public class ActivityTest extends AppCompatActivity{

    private android.support.design.widget.NavigationView navigation;
    private android.support.v4.widget.DrawerLayout drawerlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        this.drawerlayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        this.navigation = (NavigationView) findViewById(R.id.navigation);
        this.navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Toast.makeText(ActivityTest.this, "clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
