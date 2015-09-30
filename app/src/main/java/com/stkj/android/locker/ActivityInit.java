package com.stkj.android.locker;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.locker.R;

public class ActivityInit extends BaseActivity {

    private Button disableSysLocker;
    private Button start;

    @Override
    protected void onCreateImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_init);
        this.start = (Button) findViewById(R.id.start);
        this.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityMain.start(ActivityInit.this);
                finish();
            }
        });
        this.disableSysLocker = (Button) findViewById(R.id.disableSysLocker);
        this.disableSysLocker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    ComponentName cp = new ComponentName("com.android.settings", "com.android.settings.ChooseLockGeneric");
                    intent.setComponent(cp);
                    startActivityForResult(intent, 1);
                }catch (Exception e) {
                    Toast.makeText(getApplicationContext(), R.string.unable_open_locker, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ActivityInit.class));
    }

}
