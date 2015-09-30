package com.stkj.android.recyclergallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.locker.R;
import com.stkj.recyclerviewlibary.RecyclerAdapter;

/**
 * Created by jarrah on 2015/9/18.
 */
public class OnlineAdapter extends RecyclerAdapter<PhotoEntity, RecyclerView.ViewHolder> {


    public OnlineAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return OnlineViewHolder.newInstance(mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PhotoEntity entity = getItem(position);
        OnlineViewHolder viewHolder = (OnlineViewHolder) holder;
        if (entity.tag == PhotoEntity.FLAG_PACKAGE) {
            ImageHelper.displayAsset(viewHolder.image, entity.filePath);
        }

        if(PhotoEntity.getTagResource(entity) != 0) {
            viewHolder.tag.setVisibility(View.VISIBLE);
            viewHolder.tag.setImageResource(PhotoEntity.getTagResource(entity));
        }else {
            viewHolder.tag.setVisibility(View.GONE);
        }
    }

    public static class OnlineViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        ImageView tag;

        public OnlineViewHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.image);
            this.tag = (ImageView) itemView.findViewById(R.id.tag);
        }

        public static OnlineViewHolder newInstance(Context context) {
            LayoutInflater lf = LayoutInflater.from(context);
            View view = lf.inflate(R.layout.online_view_holder, null, false);
            return new OnlineViewHolder(view);
        }
    }
}
