package ir.sanatisharif.android.konkur96.utils;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.List;

import ir.sanatisharif.android.konkur96.helper.FileManager;
import ir.sanatisharif.android.konkur96.model.FileDiskModel;

public class ExistsOnSD {
    /**
     * if return true ie file is exist ito SD
     * if return false file not exist
     *
     * @param fileDiskModels
     * @return
     */
    public static void checkExistVideoToSD(@NonNull List<FileDiskModel> fileDiskModels,BooleanCallback callback) {
        for (int i = 0; i < fileDiskModels.size(); i++) {
            String url = fileDiskModels.get(i).getLink();
            
            String mediaPath = FileManager.getPathFromAllaUrl(url);
            String fileName  = FileManager.getFileNameFromUrl(url);
            File   file      = new File(FileManager.getRootPath() + mediaPath + "/" + fileName);
            if (file.exists()) {
                callback.onTrue(file.getPath());
            }
        }
        callback.onFalse();
    }
    
    public interface BooleanCallback {
        void onFalse();
        void onTrue(String path);
    }
}
