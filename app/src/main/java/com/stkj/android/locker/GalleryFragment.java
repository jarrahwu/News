package com.stkj.android.locker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.locker.R;
import com.stkj.android.recyclergallery.MyDecoration;
import com.stkj.android.recyclergallery.MyLayoutManager;
import com.stkj.android.recyclergallery.PhotoCheckedAnimator;
import com.stkj.recyclerviewlibary.RecyclerAdapter;

/**
 * Created by jarrah on 2015/9/18.
 */
public abstract class GalleryFragment<E, H extends RecyclerView.ViewHolder> extends BaseFragment implements IDrawer  {

    protected RecyclerView mRecyclerView;
    protected RecyclerAdapter<E, H> mAdapter;

    @Override
    public View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gallery_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupRecyclerView();
        onLoadGalleryImpl();
    }

    public abstract void onLoadGalleryImpl();

    public abstract void onGalleryItemClickImpl();

    private void setupRecyclerView() {
        mAdapter = getAdapter();
        mRecyclerView.setLayoutManager(new MyLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new MyDecoration());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new PhotoCheckedAnimator());
        onGalleryItemClickImpl();
    }

    public abstract RecyclerAdapter<E, H> getAdapter();

}
