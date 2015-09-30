package com.stkj.android.locker;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.ImageView;

import com.stkj.android.recyclergallery.ImageHelper;
import com.stkj.android.recyclergallery.PhotoEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Created by jarrah on 2015/9/28.
 */
public class Util {

    public static final String FILE_WALL = "wall";

    public static void saveObject(Context context, Object object, String fileName) throws IOException {
        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        fos.close();
        oos.close();
    }

    public static Object loadObject(Context context, String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = context.openFileInput(fileName);
        ObjectInputStream is = new ObjectInputStream(fis);
        Object t = is.readObject();
        is.close();
        fis.close();
        return t;
    }

    public static void savePhotoEntity(PhotoEntity entity)  {
        try {
            saveObject(Application.getInstance().getApplicationContext(), entity, FILE_WALL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PhotoEntity loadPhotoEntity() {
        try {
            PhotoEntity entity = (PhotoEntity) loadObject(Application.getInstance().getApplicationContext(), FILE_WALL);
            return entity;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void displayWallImage(ImageView lockerImage, PhotoEntity entity) {
        if(entity != null) {
            switch (entity.tag) {
                case PhotoEntity.FLAG_PACKAGE:
                    Log.from("Util", "display FLAG_PACKAGE : " + entity.filePath);
                    ImageHelper.displayAsset(lockerImage, entity.filePath);
                    break;
                case PhotoEntity.FLAG_GALLERY:
                    Log.from("Util", "display FLAG_GALLERY : " + entity.filePath);
                    ImageHelper.displayFile(lockerImage, entity.filePath);
                    break;
                default:
                    Log.from("Util", "display default : " + entity.filePath);
                    ImageHelper.displayNetwork(lockerImage, entity.filePath);
                    break;
            }
        }
    }

    public static void displayStorageWall(ImageView iv) {
        try {
            PhotoEntity entity = (PhotoEntity) loadObject(Application.getInstance().getApplicationContext(), FILE_WALL);
            displayWallImage(iv, entity);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static File saveWallPaper(Bitmap bitmap) {
        OutputStream fOut = null;
        File root = new File(Environment.getExternalStorageDirectory()
                + File.separator + "locker" + File.separator);
        root.mkdirs();
        File sdImageMainDirectory = new File(root, "wall.jpg");
        try {
            fOut = new FileOutputStream(sdImageMainDirectory);
        } catch (Exception e) {
           e.printStackTrace();
        }

        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
        }
        return sdImageMainDirectory;
    }

    public static File getWallPaper() {
        File root = new File(Environment.getExternalStorageDirectory()
                + File.separator + "locker" + File.separator);
        root.mkdirs();
        File sdImageMainDirectory = new File(root, "wall.jpg");
        return sdImageMainDirectory;
    }


    public static Bitmap getEntityBitmap(PhotoEntity entity) {
        if(entity != null) {
            switch (entity.tag) {
                case PhotoEntity.FLAG_PACKAGE:
                    File f = ImageHelper.getImageLoader().getDiskCache().get(entity.filePath);
                    Log.from("Util", "getEntityBitmap FLAG_PACKAGE : " + f.getAbsolutePath());
                    break;
                case PhotoEntity.FLAG_GALLERY:
                    Log.from("Util", "getEntityBitmap FLAG_GALLERY : " + entity.filePath);
                    break;
                default:
                    Log.from("Util", "getEntityBitmap default : " + entity.filePath);
                    break;
            }
        }
        return null;
    }
}
