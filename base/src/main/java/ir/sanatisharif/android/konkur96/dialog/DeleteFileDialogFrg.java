package ir.sanatisharif.android.konkur96.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.helper.FileManager;
import ir.sanatisharif.android.konkur96.model.Video;

/**
 * Created by Mohamad on 7/7/2017.
 */

public class DeleteFileDialogFrg extends BaseDialogFragment<DeleteFileDialogFrg> {

    private static final String           TAG    = "Alaa\\DeleteFileDialogFr";
    //------init UI
    private              View             dialog;
    private              TextView         txtOk;
    private              TextView         txtCancel;
    private              ArrayList<Video> videos = new ArrayList<>();
    private              Callback         mCallback;

    public DeleteFileDialogFrg setCallback(Callback callback) {
        mCallback = callback;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_DeviceDefault);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog = inflater.inflate(R.layout.dialog_delete_file, container, false);
        setCancelable(false);
        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtOk = dialog.findViewById(R.id.txtOk);
        txtCancel = dialog.findViewById(R.id.txtCancel);

        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videos != null)
                    notifyAndDeleteFile();
            }
        });
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    public DeleteFileDialogFrg setVideos(List<Video> v) {
        Log.i(TAG, "setVideos: " + v.size());
        videos.addAll(v);
        return this;
    }

    private void notifyAndDeleteFile() {
        Log.i(TAG, "video-size: " + videos.size());
        for (int i = 0; i < videos.size(); i++) {

            String url       = videos.get(i).getLink();
            String mediaPath = FileManager.getPathFromAllaUrl(url);
            String fileName  = FileManager.getFileNameFromUrl(url);
            Log.i(TAG, "notifyAndDeleteFile: " + fileName + " " + mediaPath);
            File file = new File(FileManager.getRootPath() + mediaPath + "/" + fileName);
            if (file.exists()) {
                file.delete();
            }
        }
        mCallback.fileDeleted();
        dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        videos.clear();
    }

    public interface Callback {
        void fileDeleted();
    }
}
