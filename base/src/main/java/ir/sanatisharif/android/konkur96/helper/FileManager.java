package ir.sanatisharif.android.konkur96.helper;

import android.net.Uri;
import android.os.Environment;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.app.AppConstants;

/**
 * Created by Mohamad on 10/30/2018.
 */

public class FileManager {

    private static final String          TAG            = "Alaa\\FileManager";
    private static       FileManager     fileManager;
    private              ArrayList<File> filesArrayList = new ArrayList<>();

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
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
               AppConstants.ROOT + File.separator;
    }

    public static String getMediaPath() {

        // External sdcard location
        return getRootPath() + AppConstants.MEDIA + File.separator;
    }

    public static String getAudioPath() {

        // External sdcard location
        return getRootPath() + AppConstants.AUDIO + File.separator;
    }

    public static String getPDFPath() {

        // External sdcard location
        return getRootPath() + AppConstants.PDF + File.separator;
    }


    /**
     * method to create root  directory
     *
     * @return root directory
     */
    public static Boolean createRootDir() {

        // External sdcard location
        String
                pathName   =
                Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
                AppConstants.ROOT + File.separator;
        File    storageDir = new File(pathName);
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

        if (url != null) {
            int index = url.lastIndexOf("/");
            return url.substring(index + 1);
        } else
            return null;
    }

    /**
     * check a single file is exisu
     *
     * @param path full path
     * @return true ie exist
     */
    public static boolean checkFileExist(String path) {

        File file = new File(path);
        return file.exists();
    }

    public static Boolean createAudioDir() {

        String pathName   = getAudioPath();
        File   storageDir = new File(pathName);
        if (!storageDir.exists()) {
            return storageDir.mkdirs();
        }
        return false;
    }

    public static Boolean createPDFDir() {

        String pathName   = getPDFPath();
        File   storageDir = new File(pathName);
        if (!storageDir.exists()) {
            return storageDir.mkdirs();
        }
        return false;
    }


    public static boolean deleteFileAndCheck(String pathName) {

        File storageDir = new File(pathName);
        if (storageDir.exists()) {
            return storageDir.delete();
        }
        return false;
    }

    public static String getPathFromAllaUrl(@NonNull String url) {
        try {
            Uri uri = Uri.parse(url);
            return uri.getPath();
        }
        catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<File> getFilesArrayList() {
        return filesArrayList;
    }

    public void clearFilesList() {
        filesArrayList.clear();
    }

    public void getFilesInDirs(File dir) {

        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    filesArrayList.add(file);
                    // Log.i("LOG", "getFilesInDirs: " + file.getPath());
                } else if (file.isDirectory()) {
                    getFilesInDirs(file.getAbsoluteFile());
                }
            }
        }
    }
}
