package com.stkj.android.locker;

import android.content.res.AssetManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.locker.R;
import com.stkj.android.recyclergallery.OnlineAdapter;
import com.stkj.android.recyclergallery.PhotoEntity;
import com.stkj.recyclerviewlibary.RecyclerAdapter;
import com.stkj.recyclerviewlibary.RecyclerItemClickListener;

import java.io.IOException;

/**
 * Created by jarrah on 2015/9/18.
 */
public class FragmentOnlineWall extends GalleryFragment<PhotoEntity, RecyclerView.ViewHolder>{

    public FragmentOnlineWall(){}

    @Override
    public void onLoadGalleryImpl() {
        try {
            loadPackageResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGalleryItemClickImpl() {
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity()) {
            @Override
            public void onItemClick(View childView, int childLayoutPosition) {
                super.onItemClick(childView, childLayoutPosition);
                ActivityPreview.start(getActivity(), mAdapter.getItem(childLayoutPosition));
            }
        });
    }


    @Override
    public RecyclerAdapter<PhotoEntity, RecyclerView.ViewHolder> getAdapter() {
        return new OnlineAdapter(getActivity());
    }


    @Override
    public int getDrawerTitle() {
        return R.string.online_wall;
    }

    private void loadPackageResource() throws IOException {
        String wallDirName = "wall";
        AssetManager manager = getActivity().getAssets();
        String[] wallFileNames = manager.list(wallDirName);

        for (String fileName : wallFileNames) {
            PhotoEntity entity = new PhotoEntity();
            entity.tag = PhotoEntity.FLAG_PACKAGE;
            entity.filePath = wallDirName + "/" + fileName;
            mAdapter.add(entity);
        }
    }
}
