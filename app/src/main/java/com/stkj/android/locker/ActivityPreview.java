package com.stkj.android.locker;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.locker.R;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.stkj.android.recyclergallery.PhotoEntity;
import com.stkj.android.view.CheckText;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jarrah on 2015/9/28.
 */
public class ActivityPreview extends AppCompatActivity{

    public static final String EXTRA_PHOTO = "photo";
    private android.widget.Button back;
    private android.widget.Button apply;
    private CheckText applyWallpaper;
    private android.widget.ImageView lockerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_preview);
        this.applyWallpaper = (CheckText) findViewById(R.id.applyWallpaper);
        this.apply = (Button) findViewById(R.id.apply);
        this.back = (Button) findViewById(R.id.back);
        this.lockerImage = (ImageView) findViewById(R.id.lockerImage);

        this.back.setOnClickListener(new OnBackClickImpl());
        this.apply.setOnClickListener(new OnApplyClickImpl());

        Util.displayWallImage(this.lockerImage, (PhotoEntity) getIntent().getSerializableExtra(EXTRA_PHOTO));
    }

    public static void start(Context context, PhotoEntity photoEntity) {
        Intent intent = new Intent();
        intent.setClass(context, ActivityPreview.class);
        intent.putExtra(EXTRA_PHOTO, photoEntity);
        context.startActivity(intent);
    }

    private class OnBackClickImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    }

    private class OnApplyClickImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            PhotoEntity entity = (PhotoEntity) getIntent().getSerializableExtra(EXTRA_PHOTO);
            if (entity != null) {
                Util.savePhotoEntity(entity);
                if (ActivityPreview.this.applyWallpaper.isChecked()) {
                    setWallpaper(entity);
                }
                finish();
            }
        }

        private void setWallpaper(PhotoEntity entity) {
            BaseImageDownloader baseImageDownloader = new BaseImageDownloader(Application.getInstance().getApplicationContext());
            try {
                InputStream is = baseImageDownloader.getStream("assets://" + entity.filePath, null);
                WallpaperManager wm = WallpaperManager.getInstance(ActivityPreview.this);
                wm.setStream(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
