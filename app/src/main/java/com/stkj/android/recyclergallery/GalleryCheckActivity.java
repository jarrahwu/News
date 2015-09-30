package com.stkj.android.recyclergallery;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.locker.R;
import com.stkj.recyclerviewlibary.RecyclerItemClickListener;

public class GalleryCheckActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 1;
    private RecyclerView mRecyclerView;
    private LocalAdapter mAdapter;
    private Loader<Cursor> mLoaderManager;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        setupActionBar();
        setupFloatingActionButton();
        setupRecyclerView();
        mLoaderManager = getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    private void setupRecyclerView() {
        mAdapter = new LocalAdapter(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new MyLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new MyDecoration());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new PhotoCheckedAnimator());

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this) {
            @Override
            public void onItemClick(View childView, int childLayoutPosition) {
                int type = mAdapter.getItem(childLayoutPosition).type;
                if (type == LocalAdapter.ViewType.CHECKED) {
                    mAdapter.getItem(childLayoutPosition).type = LocalAdapter.ViewType.NORMAL;
                } else {
                    mAdapter.getItem(childLayoutPosition).type = LocalAdapter.ViewType.CHECKED;
                }
                mAdapter.notifyItemChanged(childLayoutPosition);
            }
        });
    }

    private void setupFloatingActionButton() {
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void setupActionBar() {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        final String[] columns = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media._ID;

        return new CursorLoader(this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null) {
            Toast.makeText(this, getString(R.string.load_fail), Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()) {
                PhotoEntity item = new PhotoEntity();

                int dataColumnIndex = data
                        .getColumnIndex(MediaStore.Images.Media.DATA);
                item.filePath = data.getString(dataColumnIndex);

                mAdapter.add(item);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
