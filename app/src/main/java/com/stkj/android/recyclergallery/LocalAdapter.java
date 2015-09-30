package com.stkj.android.recyclergallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.locker.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.stkj.recyclerviewlibary.MultiTypeAdapter;

/**
 * Created by jarrah on 2015/8/17.
 */
public class LocalAdapter extends MultiTypeAdapter<PhotoEntity, RecyclerView.ViewHolder> {

    private static ImageLoader sImageLoader;

    public LocalAdapter(Context context) {
        super(context);
        ImageHelper.initImageLoader(context);
        sImageLoader = ImageHelper.getImageLoader();
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        switch (viewType) {
            case ViewType.CHECKED:
                vh = CheckedViewHolder.newInstance(mContext);
                break;
            case ViewType.NORMAL:
                vh = NormalViewHolder.newInstance(mContext);
                break;
            default:
                vh = NormalViewHolder.newInstance(mContext);
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PhotoEntity entity = getItem(position);
        switch (entity.getTypeId()) {
            case ViewType.NORMAL:
                bindNormalViewHolder((NormalViewHolder) holder, entity, position);
                break;
            case ViewType.CHECKED:
                bindCheckedViewHolder((CheckedViewHolder) holder, entity, position);
                break;
        }
    }

    private void bindCheckedViewHolder(final CheckedViewHolder holder, PhotoEntity entity, int position) {
        sImageLoader.displayImage("file:///" + entity.filePath, holder.image);
    }

    private void bindNormalViewHolder(final NormalViewHolder holder, PhotoEntity entity, int position) {
        sImageLoader.displayImage("file:///" + entity.filePath, holder.image);
    }

    public static class ViewType {
        public static final int NORMAL = 0x101;
        public static final int CHECKED = 0x102;
    }

    private static class CheckedViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public CheckedViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
        }

        public static RecyclerView.ViewHolder newInstance(Context mContext) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.checked_image_holder, null, false);
            return new CheckedViewHolder(view);
        }
    }

    private static class NormalViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public NormalViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
        }

        public static RecyclerView.ViewHolder newInstance(Context mContext) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.normal_image_holder, null, false);
            return new NormalViewHolder(view);
        }
    }
}
