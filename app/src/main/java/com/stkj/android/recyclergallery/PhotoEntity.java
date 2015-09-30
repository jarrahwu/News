package com.stkj.android.recyclergallery;

import com.example.locker.R;
import com.stkj.recyclerviewlibary.MultiTypeAdapter;

import java.io.Serializable;

/**
 * Created by jarrah on 2015/8/17.
 */
public class PhotoEntity implements MultiTypeAdapter.TypeMapping, Serializable {

    public static final int FLAG_PACKAGE = 1;//内置
    public static final int FLAG_NORMAL = 2;//普通
    public static final int FLAG_DOWNLOADED = 3;//已经下载
    public static final int FLAG_FESTIVAL = 4;//节日
    public static final int FLAG_USING = 5;//正在使用
    public static final int FLAG_RECOMMEND = 6;//推荐
    public static final int FLAG_GALLERY = 7; //相册

    public int type = LocalAdapter.ViewType.NORMAL;

    public int tag;

    public String filePath;

    @Override
    public int getTypeId() {
        return type;
    }

    public static int getTagResource(PhotoEntity entity) {
        int res = 0;
        switch (entity.tag) {
            case FLAG_PACKAGE:
            case FLAG_DOWNLOADED:
                res = R.drawable.ic_tag_downloaded;
                break;
            case FLAG_FESTIVAL:
                res = R.drawable.ic_tag_festival;
                break;
            case FLAG_NORMAL:
                res = 0;
                break;
            case FLAG_USING:
                res = R.drawable.ic_tag_using;
                break;
            case FLAG_RECOMMEND:
                res = R.drawable.ic_tag_recommend;
                break;
        }
        return res;
    }

    @Override
    public int hashCode() {
        if (this.filePath == null) {
            return 17;
        }
        return this.filePath.hashCode() + this.tag + this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PhotoEntity)) {
            return false;
        }

        PhotoEntity e = (PhotoEntity) o;

        return this.hashCode() == e.hashCode();
    }
}
