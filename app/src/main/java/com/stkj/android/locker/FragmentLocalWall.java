package com.stkj.android.locker;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.locker.R;
import com.stkj.android.recyclergallery.LocalAdapter;
import com.stkj.android.recyclergallery.PhotoEntity;
import com.stkj.recyclerviewlibary.RecyclerAdapter;
import com.stkj.recyclerviewlibary.RecyclerItemClickListener;

/**
 * Created by jarrah on 2015/9/18.
 */
public class FragmentLocalWall extends GalleryFragment<PhotoEntity, RecyclerView.ViewHolder> implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int LOADER_ID = 1;
    private Loader<Cursor> mLoaderManager;

    public FragmentLocalWall() {

    }


    @Override
    public void onGalleryItemClickImpl() {
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity()) {
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

    @Override
    public RecyclerAdapter<PhotoEntity, RecyclerView.ViewHolder> getAdapter() {
        return new LocalAdapter(getActivity());
    }

    @Override
    public void onLoadGalleryImpl() {
        mLoaderManager = getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        final String[] columns = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media._ID;
        return new CursorLoader(getActivity(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null) {
            Toast.makeText(getActivity(), getString(R.string.load_fail), Toast.LENGTH_SHORT).show();
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

    @Override
    public int getDrawerTitle() {
        return R.string.local_wall;
    }
}
