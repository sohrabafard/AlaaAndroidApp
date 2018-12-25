package ir.sanatisharif.android.konkur96.helper;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;

/**
 * Created by Mohamad on 10/30/2018.
 */

public class FileManager {

    private ArrayList<File> filesArrayList = new ArrayList<>();

    private static FileManager fileManager;

    public static FileManager getInstance() {
        if (fileManager == null)
            fileManager = new FileManager();
        return fileManager;
    }

    /**
     * method to create root directory
     *
     * @return root directory
     */
    public static String getRootPath() {

        // External sdcard location
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + AppConstants.ROOT + File.separator;
    }

    public static String getMediaPath() {

        // External sdcard location
        return getRootPath() + AppConstants.MEDIA + File.separator;
    }

    public static String getAudioPath() {

        // External sdcard location
        return getRootPath() + AppConstants.AUDIO + File.separator;
    }


    /**
     * method to create root  directory
     *
     * @return root directory
     */
    public static Boolean createRootDir() {

        // External sdcard location
        String pathName = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + AppConstants.ROOT + File.separator;
        File storageDir = new File(pathName);
        if (!storageDir.exists()) {
            return storageDir.mkdirs();
        }
        return false;
    }

    /**
     * get fileName form Url  https://developer.android.com/images/training/appbar/appbar_basic.png -> appbar_basic.png
     *
     * @param url
     * @return
     */
    public static String getFileNameFromUrl(String url) {

        int index = url.lastIndexOf("/");

        return url.substring(index + 1);
    }

    /**
     * check a single file is exisu
     *
     * @param path full path
     * @return true ie exist
     */
    public static boolean checkFileExist(String path) {

        File file = new File(path);
        if (file.exists())
            return true;
        return false;
    }

    public static Boolean createAudioDir() {

        String pathName = getAudioPath();
        File storageDir = new File(pathName);
        if (!storageDir.exists()) {
            return storageDir.mkdirs();
        }
        return false;
    }

   /* public static List<File> getFileList() {

        File directory = new File(getVideoPath());
        File[] f = directory.listFiles();
        if (f.length == 0) {
            return null;
        }
        return Arrays.asList(f);
    }*/

    public static boolean deleteFileAndCheck(String pathName) {

        File storageDir = new File(pathName);
        if (storageDir.exists()) {
            return storageDir.delete();
        }
        return false;
    }

    public static String getPathFromAllaUrl(String url) {

        int start = url.indexOf("sanatisharif.ir/");
        int end = url.lastIndexOf("/");

        return url.substring(start + 16, end + 1);
    }

    public ArrayList<File> getFilesArrayList() {
        return filesArrayList;
    }

    public void clearFilesList() {
        filesArrayList.clear();
    }

    public void getFilesInDirs(File dir) {

        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                filesArrayList.add(file);
              //  Log.i("LOG", "getFilesInDirs: " + file.getPath());
            } else if (file.isDirectory()) {
                getFilesInDirs(file.getAbsoluteFile());
            }
        }
    }
}
